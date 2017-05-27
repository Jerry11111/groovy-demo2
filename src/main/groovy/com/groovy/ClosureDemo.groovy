package com.groovy

import com.sun.net.httpserver.Base64;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;


class SizeFilter {
	Integer limit
	boolean sizeUpTo(String value) {
		return value.size() <= limit
	}
}

def doubleMethod (entry){
	entry.value = entry.value * 2
}
def decClosure(){
	// 1. declare closure
	log = ''
	(1..10).each{ counter -> log += counter }
	assert log == '12345678910'
	// 2. declare closure
	log = ''
	(1..10).each{ log += it }
	assert log == '12345678910'
	// 3. 闭包赋值变量
	def printer = { line -> println line }
	printer 'abc'
	// 4. 方法转化为闭包
	SizeFilter filter6 = new SizeFilter(limit:6)
	SizeFilter filter5 = new SizeFilter(limit:5)
	Closure sizeUpTo6 = filter6.&sizeUpTo
	def words = ['long string', 'medium', 'short', 'tiny']
	assert 'medium' == words.find (sizeUpTo6)
	assert 'short' == words.find (filter5.&sizeUpTo)
	
	// 5. 
	Map map = ['a':1, 'b':2]
	map.each{ key, value -> map[key] = value * 2 } // double map的值
	assert map == ['a':2, 'b':4]
	Closure doubler = {key, value -> map[key] = value * 2 }
	map.each(doubler)
	assert map == ['a':4, 'b':8]
	doubler = this.&doubleMethod
	map.each(doubler)
	assert map == ['a':8, 'b':16]
}

def benchmark(int repeat, Closure worker) {
	def start = System.nanoTime()
	repeat.times { worker(it) }
	def stop = System.nanoTime()
	return stop - start
}

def userClosure(){
	def adder = { x, y -> return x+y }
	assert adder(4, 3) == 7
	assert adder.call(2, 6) == 8
	
	def slow = benchmark(10000) { (int) it / 2 }
	def fast = benchmark(10000) { it.intdiv(2) }
	assert fast * 2 < slow;
	
	//闭包参数定义默认值
	adder = { x, y=5 -> return x+y }
	assert adder(4, 3) == 7
	assert adder.call(7) == 12
}

def base(){
	// 但是用times方法来做0到n-1的循环可能会影响语义，所以最好还是根据实际情况使用
	for (i = 0; i < 5; ++i) {
		println i
	}
	for (i in 0..<5) {
		println i
	}
	5.times { println 'Yes' }
	5.times { println it } // 输出“0 1 2 3 4”而不是“1 2 3 4 5”
	5.times { i -> println i}
}

userClosure();
