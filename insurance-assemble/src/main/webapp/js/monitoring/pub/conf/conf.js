var language = navigator.language;  
if(!language) language = navigator.systemLanguage;

if(language.search("-")>-1) language = language.replace("-","_"); 
language = 'zh_CN';
var _cf =
{
	webcu_ver:"",
	debug : true,					// 是否打开调试状态
	
	type : 'embed',					// 类型[embed:内置网页,external：客户端内部调用,handeye：掌中眼,decode:解码器]
	
	theme: 'gray',					//皮肤[black:黑，gray：白]
	
	language : language,			// 默认语言[en,zh_CN]
	
	path:'../',						// root path
	
	autoLogin:false,				// 如果设置为true,客户端将会用下面的loginParams参数自动登录平台,如果登录失败,会打开登录框
	
	bFix:'1',						// 是否通过网闸登录平台

	authType:'2',					//['':默认,1：掌中眼,2:宝宝在线平台]

	defaultTabIndex:0,
	authType:'',					//['':默认,1：掌中眼,2:宝宝在线平台]	
	title:'',
	mapType:'amap',					// [amap,bmap,gmap]
	loginParam:{
		epId:'system',
		ip:'',
		port:'9988',
		username:'admin',
		password:'',
		remember:true,
		selected:true
	}
//迅雷会员账号627312313:2密1797515	
}