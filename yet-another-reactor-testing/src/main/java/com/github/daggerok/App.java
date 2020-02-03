package com.github.daggerok;

import lombok.extern.log4j.Log4j2;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.BeanManager;

@Log4j2
public class App {
  public static void main(String[] args) {
    log.info("Hello!");
    SeContainerInitializer.newInstance()
                          .setClassLoader(App.class.getClassLoader())
                          // require <exclude name="org.jboss.weld.**"/> into beans.xml
                          .addPackages(true, App.class, BeanManager.class)
                          .initialize();
  }
}
