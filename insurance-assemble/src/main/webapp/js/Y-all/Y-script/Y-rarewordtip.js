
(function($){

define(function(require, exports, module){
	require("./Y-tip");

	Y.inherit('RareWordTip','Tip',{
		doInit: function(cfg){
			var contentHtml = '<div class="unfamiliar unfamiliarBox"';
			contentHtml += 'style="width: 400px; border:1px solid #999; background:#fff; padding:5px; z-index: 8; position: absolute; left: 410px; top: 255px;">';
			contentHtml += '<div class="unfamiliar-spells"><a title="a" class="unfamiliar-spell selected" href="javascript:;">a</a>';
			var arr = ['b','c[ch]','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s[sh]','t','u','v','w','x','y'];
			for(var i=0;i<arr.length;i++) {
			    contentHtml += '<a title="'+arr[i]+'" class="unfamiliar-spell" href="javascript:;">'+arr[i]+'</a>';
			}
			contentHtml += '<a title="z" class="unfamiliar-spell" href="#">z[zh]</a></div><div class="unfamiliar-words"><a style="z-index:10;" class="unfamiliar-word" href="#">奡</a>';
			contentHtml += '<a style="z-index:10;" class="unfamiliar-word" href="#">靉</a><a style="z-index:10;" class="unfamiliar-word" href="#">叆</a></div></div>';
			var content = $(contentHtml).hide();
			$.extend(cfg,{
			    content: content,
				renderTo: 'body',
				closeAction: 'hide'
			});
			var input = this.toJqObj(cfg.target);
			var _this = this;
			if(cfg.showEle) {
			    var showBtn = this.toJqObj(cfg.showEle);
				this.bind(showBtn,'click',function(e){
				    _this.show();
					e.stopPropagation();
				});
			}
			this.input = input;
			this.callBase('doInit','Tip',cfg);
		},
		doRender: function(){
		    var state = this.callBase('doRender','Tip');
			var dict = {
			    a:"奡靉叆",c:"旵玚棽琤翀珵楮偲赪瑒篪珹捵茝鷐铖宬査嶒",b:"仌昺竝霦犇愊贲琲礴埗別骉錶",
			    d:"耑昳菂頔遆珰龘俤叇槙璗惇",g:"玍冮芶姏堽粿筦嘏釭",f:"仹汎沨昉璠雰峯洑茀渢棻棻頫",
				e:"峩",h:"郃浛訸嗃瓛翃隺鋐滈翚翯竑姮葓皜袆淏皞翙銲鉷澒澔閤婳黃峘鸻鈜褘锽谹嫮",
				k:"凱堃蒯鹍崑焜姱衎鵾愷鎧",j:"冏泂劼莙濬暕珒椈珺璟競煚傑玦鑑瑨瑨琎勣寯烱浕斚倢瑴畯雋傢峤",
				m:"劢忞旻旼濛嫚媺铓鋩洺媌媔祃牻慜霂楙媄瑂",l:"玏呂俍冧倞琍綝壘孋瓅璘粦琍麗樑秝鍊崚链镠皊箖菻竻鸰琭瓈騄浬瑠嶺稜欐昽",
				n:"婻寗嫟秾迺柟薿枏",q:"玘佺耹踆骎啟蒨慬勍嵚婍璆碏焌駸綪锜荍釥嶔啓",p:"芃玭玶罴毰珮蘋慿弸掽逄砯",
				s:"屾昇妽珅姼甡湦骦塽挻甦鉥燊遂陞莦湜奭佀聖骕琡",r:"汭瑈瑢讱镕婼叡蒻羢瀼",
				t:"沺凃禔慆弢颋譚曈榃湉珽瑱橦镋渟黇頲畑媞鰧",w:"卍彣炆溦娬韡暐偉湋妏硙珷娒",
				y:"乂冘弌贠伝伃杙沄旸玙玥垚訚堯溁嫈澐颺熤儀赟祎瑀湧燚嬿鋆嫄愔贇彧崟韻龑颙晹媖顒禕羕炀弇湲霙嫕浥飏峣曣億雲愔洢暘钖垟詠燿鹓歈貟瑩燏暎畇娫矞祐溳崯颍煬靷谳異軏繄",
				x:"仚旴忺炘昍烜爔斅豨勲敩虓鈃禤燮瑄晞賢翾譞諕璿琇晛焮珣晅郤禼皛哓肸谞迿咲婞昫缐姁猇欻箮翛暁",
				z:"烝梽喆禛誌曌衠淽枬詟炤昝珘赒"
			};
		    var charts = this.item.find('.unfamiliar-spells a');
			var _this = this;
			this.bind(charts,'mouseover',function(){
			    charts.removeClass('rarewordtip-on');
				$(this).addClass('rarewordtip-on');
				var html = '';
				var str = dict[$(this).attr('title')];
				if(str) {
				    for (i = 0; i < str.length; i++){
					    html += '<a style="z-index:10;margin-right:5px;" class="unfamiliar-word" href="#">'+ str.charAt(i) +'</a>';
					};
					
				} else {
				    html += '<a style="z-index:10;margin-right:5px;" class="unfamiliar-word" href="#">&nbsp</a>'
				}
				_this.el.find('.unfamiliar-words').html(html);
			});
			var input = this.input;
			this.el.delegate('.unfamiliar-words a','click', function(e){
				e.preventDefault();
				input.focus();
				input.val(input.val() + $(this).html());
			});
		}
		
	});
    Y.RareWordTip.defaults = $.extend({},Y.Tip.defaults, {
		autoDisappear:true,
		css: 'wnd-tip-rarewordtip',
		autoShow: false
	});
});
  
})($);