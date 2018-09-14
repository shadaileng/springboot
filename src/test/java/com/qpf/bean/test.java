package com.qpf.bean;

import org.junit.Test;

public class test {
    public class Parent {
        public Parent() {}
        public Parent(String name) {
            this.name = name;
        }
        String name;
    }
    public interface ParentIn {
        String getName();
    }

    public class A extends Parent implements ParentIn{
        public A(String name) {
            super(name);
        }
        @Override
        public String getName() {
            return this.name;
        }
    }
    @Test
    public void test() {
        ParentIn a = new A("abc");
        System.out.println(a.getName());
        System.out.println(a instanceof Parent);
        System.out.println(a instanceof ParentIn);
    }
}
