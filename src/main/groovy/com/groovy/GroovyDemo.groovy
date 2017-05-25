package com.groovy

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.URIBuilder

println 'Hello World!'

/*
def http = new HTTPBuilder('http://www.google.com.hk')
http.request(Method.GET, ContentType.TEXT) {
	uri.path="/search"
	uri.query = [q:'groovy']
  
	response.success ={resp,reader->
		println resp.statusLine.statusCode
		println resp.headers.'content-length'
		System.out << reader
	 }
	 response.failure={resp-> println resp.status }
}*/
def http = new HTTPBuilder('http://www.baidu.com')
http.request(Method.GET,ContentType.TEXT) {
	//设置url相关信息
	uri.path='/'
	uri.query=[a:'1',b:2]
	//设置请求头信息
	headers.'User-Agent' = 'Mozill/5.0'
	//设置成功响应的处理闭包
	response.success= {resp,reader->
		 println resp.status
		 println resp.statusLine.statusCode
		 println resp.headers.'content-length'
		 //System.out << reader
	 }
	 //根据响应状态码分别指定处理闭包
	 response.'404' = { println 'not found' }
	 //未根据响应码指定的失败处理闭包
	 response.failure = { println "Unexpected failure: ${resp.statusLine}" }
 }