mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-common-util -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-dal -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-facade -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-domain -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-biz -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-web -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-integration -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-assemble -Dversion=1.0.0.20130604
mvn archetype:generate -DgroupId=com.yjf.eloan -DartifactId=eloan-test -Dversion=1.0.0.20130604


mvn eclipse:eclipse -DdownloadSources=true

每次提交代码之前，执行
mvn install -Dmaven.test.skip=true

在eclipse里面只要执行
com.yjf.enterprise.test.base.TestAllSmockUnitSuit
即可跑所有用例，每次都要保证每个用例都是通过的。


#清理自动生成#
rm -rf ./eloan-assemble/src/test/ ./eloan-facade/src/test/ ./eloan-biz/src/test/ ./eloan-web/src/test/ ./eloan-common-util/src/test/ ./eloan-dal/src/test/ ./eloan-integration/src/test/ ./eloan-domain/src/test/  ;

rm -rf ./eloan-assemble/src/main/java/com/yjf/ppm/App.java ./eloan-facade/src/main/java/com/yjf/ppm/App.java ./eloan-biz/src/main/java/com/yjf/ppm/App.java ./eloan-web/src/main/java/com/yjf/ppm/App.java ./eloan-common-util/src/main/java/com/yjf/ppm/App.java ./eloan-dal/src/main/java/com/yjf/ppm/App.java ./eloan-integration/src/main/java/com/yjf/ppm/App.java ;

rm -rf ./eloan-assemble-test/src/main/ ./eloan-test-test/src/main/;

rm -rf ./eloan-assemble-test/src/test/java/com/yjf/ppm/AppTest.java ./eloan-test-test/src/test/java/com/yjf/ppm/AppTest.java;
