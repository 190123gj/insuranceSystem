
(function($){

define(function(require){
	require("./Y-base");
	Y.inherit('TreeNode','box',{
	    doInit: function(cfg){  
		    var _this = this;
		    cfg.renderTo = cfg.renderTo || cfg.target || 'body';
			this.value = cfg.value,this.text = cfg.text;
            var el = $("<li>").addClass('tree-node')
			        .append($("<a>").attr('href',cfg.link || 'javascript:;')
					        .append($("<span>").addClass('tree-node-del').html('x').attr('title',this.strings.delete).css('cursor','pointer'))
							.append($("<sapn>").addClass('tree-node-text').html(this.text || ''))
					        
					);
			if((cfg.leaf && cfg.delAble === false) || (!cfg.leaf && cfg.delAble !== true)) {
			    el.find('.tree-node-del').hide();
			}
			this.el = el;
			if(!cfg.leaf) {
			    var child = $("<ul class='tree-node-child'></ul>").hide();
				this.childEl = child;
				el.append(child).find('a').addClass('tree-node-collapse');
				this.collapsing = true;
			} else {
			    el.addClass('tree-node-leaf');
				this.leaf = true;
			}
			this.bind(el.find('a'),'click',function(e){
				if(e.target == $(this).find('.tree-node-del').get(0)) {
				    return;
				}
				if(_this.fire('beforeclick',_this.value,_this.text,_this)===false) {
				    return;
				};
				if(_this.expanding) {
					_this.collapse();
				} else {
					_this.expand();
				}
				_this.fire('click',_this.value,_this.text,_this)
			});
			this.bind(el.find('.tree-node-del'),'click',function(){
			    if(_this.parentNode) {
				    _this.parentNode.removeChild(_this);
				} else {
				    _this.close();
				}
				_this.fire('delete',_this.value,_this.text,_this);
			});
			this.tag = cfg.tag;
			this.childNodes = [];
		},
		appendChild: function(cfg){
		    var _this = this;
		    var node = Y.create("TreeNode",$.extend({},cfg,{
			    renderTo: this.childEl
			},this.cfg.defaults || {}));
			node.show();
			this.childNodes.push(node);
			node.parentNode = this;
			node.tree = this.tree;
			if(this.tree) this.tree.fire('appendnode',node.value,node.text,node);
			return node;
		},
		expand:function(){
		    if(this.expandAble === false) {
			    return;
			}
		    if(this.leaf) {
			    return;
			}
		    this.el.children('a').removeClass('tree-node-collapse').addClass('tree-node-expand');
			if(this.childNodes.length > 0) {
			    this.childEl.slideDown();
			} else {
			    this.childEl.show();
			}
			this.expanding = true;
			this.fire('expand');
		},
		collapse: function(){
		    this.childEl.slideUp();
			this.el.children('a').removeClass('tree-node-expand').addClass('tree-node-collapse');
			this.expanding = false;
			this.fire('collapse');
		},
		removeChild: function(node){
		    node = typeof node == 'string'?this.findChild(node):node;
			if(!node.isDestroy) node.close();
			this.childNodes.slice(this.findIndex(node),1);
			this.fire('removechild',node.value,node.text,node);
		},
		findChild:function(value){
		    return $.grep(this.childNodes,function(node,i){
			    return node.value === value;
			})[0];
		},
		findIndex:function(node){
		    node = typeof node == 'string'?this.findChild(node):node;
		    return $.inArray(node,this.childNodes);
		},
		clearChild:function(){
		    var _this = this;
		    $.each(this.childNodes,function(i,item){
			    _this.removeChild(item);
			});
			this.childNodes = [];
		}
		
	});
	
	Y.inherit('Tree','TreeNode',{
	    doInit: function(cfg){  
            this.tree = this;	
            var _this = this;			
		    cfg.renderTo = cfg.renderTo || cfg.target || 'body';
            var el = $("<ul>").addClass('tree-tree');
			this.el = el;
			this.childNodes = [];
			this.childEl = el;
			this.on('appendnode',function(v,t,aNode){
			    if(cfg.dataUrl && !aNode.leaf) {
				    aNode.on('expand',function(){
					    if(!aNode.dataLoad) {
						    setTimeout(function(){
							    aNode.childEl.css('visibility','hidden');
								aNode.expandAble = false;
							    _this.loadData(aNode,function(){
							        aNode.childEl.css({visibility:'',display:'none'});
									aNode.expandAble = true;
								    aNode.expand();
							    });
								setTimeout(function(){
								    aNode.expandAble = true;
								},5000);
							},10);
			            }
				    });
				}
				aNode.on('beforeclick',function(){
				    return _this.fire('beforeclick',aNode.value,aNode.text,aNode);
				});
				aNode.on('click',function(){
				    _this.fire('click',aNode.value,aNode.text,aNode);
				    if(!aNode.leaf) return;
				    _this.el.find('li').removeClass('tree-node-selected');
				    aNode.el.addClass('tree-node-selected');
				});
				aNode.on('delete',function(){
				    _this.fire('delete',aNode.value,aNode.text,aNode);
				});
			});
			if(cfg.data) {
			    this.setStore(cfg.data);
			}
		},
		setStore:function(data,node){
		    node = node || this;
			node.clearChild();
		    if(!$.isArray(data)) {
			    Y.handlerError("无效的数据源");
				return false;
			}
			var _this = this;
			var cfg = this.cfg;
		    $.each(data,function(i,item){
		        var nodeCfg = $.extend({},cfg.defaults || {},item);
			    var aNode = node.appendChild(nodeCfg); 
				if(item.data) {
				    _this.setStore(item.data,aNode);
				}
			});
		},
		loadData: function(node,callback){
		    var cfg = this.cfg;
		    var _this = this;
		    var param = {};
			if(cfg.dataParam) {
				param = typeof cfg.dataParam =='function'?cfg.dataParam(node):cfg.dataParam;
			}
			param.value = node.value;
			$.ajax({
			    url:cfg.dataUrl,
			    data:param,
				cache:false,
				dataType: cfg.dataType || 'json',
			    success:function(res){
				    var data = cfg.dataHandler?cfg.dataHandler(res):res;
					_this.setStore(data,node);
				    node.dataLoad = true;
				    if(callback) {
				        callback(true);
				    }
				},error: function(){
				    if(callback) {
				        callback(false);
				    }
				}
			});
		}
	});
    Y.TreeNode.defaults = {
	    autoShow: true
	};
	Y.Tree.defaults = {
	    autoShow: true
	}
});

})($);