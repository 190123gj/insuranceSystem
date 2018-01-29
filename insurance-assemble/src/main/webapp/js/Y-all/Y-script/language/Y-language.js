/**
 * @fileoverview 语言包加载文件
 * @author xiaoyangzheng
 * @modify 
 * @requires
 * @description   
 * @created  2014-12-15
 * @updated  
 */

define(function(require, exports, module){
    Y.language = Y.language || {};
    $.extend(Y.language, {
        name: '',
        pact: {},
        getPact: function(name){
            if(!this.name) return;
            if(!name) return this.pact[this.name];
            return (this.pact[this.name]&&this.pact[this.name][name]) || {};
        }
    });
	require('./Y-language-zh.js');
	require('./Y-language-en.js');
});
