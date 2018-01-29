<?php
define('ERROR_REPORT_LEVEL',0);
header('Content-Type:text/html;charset=utf-8');
ini_set("soap.wsdl_cache_enabled", 0);

$authtype = "";
if(array_key_exists("authtype",$_POST))
{
	$authtype = $_POST["authtype"];
}

$username = "";
if(array_key_exists("username",$_POST))
{
	$username = $_POST["username"];
}

$password = "";
if(array_key_exists("password",$_POST))
{
	$password = $_POST["password"];
}

if($authtype == 2){
	$soap = new SoapClient('http://223.100.49.193:9580/BabyOnline/service/bbol_service.wsdl');
	$params = array(array('requestJSON'=>"{\"action\":\"web_login\",\"content\":{\"phone\":\"$username\",\"password\":\"".strtoupper(md5($password))."\",\"clientType\":\"WebCU\"}}"));
	$rv = $soap->__call('getRequestJSON',$params);
	
	$response = $rv->resultJSON;
	echo $response;
}
else{
	$soap = new SoapClient('http://172.16.2.3:9580/handeye/service/handeye_service.wsdl');
	$params = array(array('requestJSON'=>"{\"action\":\"login\",\"content\":{\"phone\":\"$username\",\"password\":\"".md5($password)."\",\"clientType\":\"WebCU\"}}"));//
	$rv = $soap->__call('getRequestJSON',$params);
	
	$response = $rv->resultJSON;
	echo $response;
}
?>