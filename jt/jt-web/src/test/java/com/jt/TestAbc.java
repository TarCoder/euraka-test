package com.jt;

public class TestAbc {
	
	public static void main(String[] args) {
		B b = new B();
		b.testb();
	}
}


class A{
	public void testa() {
		System.out.println("A.testa()");
	}
	
	public void testb() {
		System.out.println("A.testb()");
		testa();
	}
}

class B extends A {
	@Override
	public void testb() {
		super.testb();
		System.out.println("B.testb()");
	}
	
	@Override
	public void testa() {
		System.out.println("B.testa()");
	}
}



