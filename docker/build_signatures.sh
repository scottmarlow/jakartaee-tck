#!/bin/bash -xe

# Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0

#
# Usage:  run docker/build_signatures.sh from root of Jakarta EE Platform TCK source project to generate signature map files.
#          bash -x docker/build_signatures.sh  2>&1 | tee /tmp/sign.log
# 
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$PATH

if [ -z "$WORKSPACE" ]; then
  export WORKSPACE=`pwd`
fi

cd $WORKSPACE
export BASEDIR=`pwd`
export TS_HOME=$BASEDIR

if [ -z "$JAKARTA_JARS" ]; then
  export JAKARTA_JARS=$BASEDIR/modules
fi

echo "JAKARTA_JARS = $JAKARTA_JARS"
export TS_HOME=$BASEDIR

which java
java -version

cd $BASEDIR

mkdir -p $JAKARTA_JARS
cd $JAKARTA_JARS
mvn -f $BASEDIR/docker/pom.xml dependency:copy-dependencies -DoutputDirectory="${TS_HOME}/modules" -Dmdep.stripVersion=true

echo "generate TCK signature map files"
cd $TS_HOME/lib
# replace existing (https://wiki.openjdk.java.net/display/CodeTools/sigtest) sigtest.jar with latest jar from (fork) https://github.com/jtulach/netbeans-apitest
mvn dependency:get -Dartifact=org.netbeans.tools:sigtest-maven-plugin:LATEST:jar -Dtransitive=false -Ddest=sigtest.jar
export sigtest_classes="${TS_HOME}/lib/sigtest.jar"
export pathsep=:
export deliverabledir=signature-repository
mkdir -p $BASEDIR/install/$deliverabledir
export tckname=jakartaee

cd $TS_HOME/src/com/sun/ts/tests/signaturetest/signature-repository

# package list copied from install/jakartaee/bin/sig-test-pkg-list_se8.txt
export INCLUDES="java.io:java.lang:java.lang.Enum<{java.lang:java.lang.annotation:java.rmi:java.security:java.sql:java.text:java.util:java.util.concurrent:jakarta.activation:jakarta.annotation:javax.annotation.processing:jakarta.annotation.security:jakarta.annotation.sql:jakarta.batch.api:jakarta.batch.api.chunk:jakarta.batch.api.chunk.listener:jakarta.batch.api.listener:jakarta.batch.api.partition:jakarta.batch.operations:jakarta.batch.runtime:jakarta.batch.runtime.context:jakarta.decorator:jakarta.ejb:jakarta.ejb.embeddable:jakarta.ejb.spi:jakarta.el:jakarta.enterprise.concurrent:jakarta.enterprise.context:jakarta.enterprise.context.control:jakarta.enterprise.context.spi:jakarta.enterprise.event:jakarta.enterprise.inject:jakarta.enterprise.inject.literal:jakarta.enterprise.inject.spi:jakarta.enterprise.inject.spi.configurator:jakarta.enterprise.util:jakarta.faces:jakarta.faces.application:jakarta.faces.bean:jakarta.faces.component:jakarta.faces.component.behavior:jakarta.faces.component.html:jakarta.faces.component.search:jakarta.faces.component.visit:jakarta.faces.context:jakarta.faces.convert:jakarta.faces.el:jakarta.faces.event:jakarta.faces.flow:jakarta.faces.flow.builder:jakarta.faces.lifecycle:jakarta.faces.model:jakarta.faces.push:jakarta.faces.render:jakarta.faces.validator:jakarta.faces.view:jakarta.faces.view.facelets:jakarta.faces.webapp:jakarta.inject:jakarta.interceptor:jakarta.jms:jakarta.json:jakarta.json.bind:jakarta.json.bind.adapter:jakarta.json.bind.annotation:jakarta.json.bind.config:jakarta.json.bind.serializer:jakarta.json.bind.spi:jakarta.json.spi:jakarta.json.stream:jakarta.jws:jakarta.jws.soap:jakarta.mail:jakarta.mail.event:jakarta.mail.internet:jakarta.mail.search:jakarta.mail.util:javax.naming:jakarta.persistence:jakarta.persistence.criteria:jakarta.persistence.metamodel:jakarta.persistence.spi:jakarta.resource:jakarta.resource.cci:jakarta.resource.spi:jakarta.resource.spi.endpoint:jakarta.resource.spi.security:jakarta.resource.spi.work:javax.security.auth.callback:javax.security.auth.login:jakarta.security.auth.message:jakarta.security.auth.message.callback:jakarta.security.auth.message.config:jakarta.security.auth.message.module:jakarta.security.enterprise:jakarta.security.enterprise.authentication.mechanism.http:jakarta.security.enterprise.credential:jakarta.security.enterprise.identitystore:jakarta.security.jacc:jakarta.servlet:jakarta.servlet.annotation:jakarta.servlet.descriptor:jakarta.servlet.http:jakarta.servlet.jsp:jakarta.servlet.jsp.el:jakarta.servlet.jsp.jstl.core:jakarta.servlet.jsp.jstl.fmt:jakarta.servlet.jsp.jstl.sql:jakarta.servlet.jsp.jstl.tlv:jakarta.servlet.jsp.tagext:jakarta.transaction:javax.transaction.xa:jakarta.validation:jakarta.validation.Configuration<{jakarta.validation:jakarta.validation.Configuration<{jakarta.validation.bootstrap:jakarta.validation.Configuration<{jakarta.validation.spi:jakarta.validation.bootstrap:jakarta.validation.constraints:jakarta.validation.constraintvalidation:jakarta.validation.executable:jakarta.validation.groups:jakarta.validation.metadata:jakarta.validation.spi:jakarta.validation.valueextraction:jakarta.websocket:jakarta.websocket.server:jakarta.ws.rs:jakarta.ws.rs.client:jakarta.ws.rs.container:jakarta.ws.rs.core:jakarta.ws.rs.ext:jakarta.ws.rs.sse:jakarta.xml.bind:jakarta.xml.bind.annotation:jakarta.xml.bind.annotation.adapters:jakarta.xml.bind.attachment:jakarta.xml.bind.helpers:jakarta.xml.bind.util:jakarta.xml.soap:javax.xml.transform:javax.xml.transform.dom:javax.xml.transform.sax:jakarta.xml.ws:jakarta.xml.ws.handler:jakarta.xml.ws.handler.soap:jakarta.xml.ws.http:jakarta.xml.ws.soap:jakarta.xml.ws.spi:jakarta.xml.ws.spi.http:jakarta.xml.ws.wsaddressing:org.w3c.dom:org.xml.sax"

export sigTestClasspath="$JAKARTA_JARS/glassfish-corba-omgapi.jar${pathsep}\
$JAKARTA_JARS/jakarta.activation-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.activation.jar${pathsep}\
$JAKARTA_JARS/jakarta.annotation-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.authentication-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.authorization-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.batch-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.ejb-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.el-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.enterprise.cdi-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.enterprise.concurrent-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.faces-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.inject-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.interceptor-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.jms-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.json-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.json.bind-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.mail-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.persistence-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.resource-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.security.enterprise-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.servlet-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.servlet.jsp-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.servlet.jsp.jstl-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.transaction-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.validation-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.websocket-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.ws.rs-api.jar${pathsep}\
$JAKARTA_JARS/jakarta.xml.bind-api.jar${pathsep}\
$JAKARTA_JARS/webservices-api.jar${pathsep}\
$JAKARTA_JARS/webservices-api-osgi.jar${pathsep}\
${JAVA_HOME}/jre/lib/rt.jar${pathsep}\
${JAVA_HOME}/jre/lib/jce.jar"

#echo "sigTestClasspath = $sigTestClasspath"

#export OPTIONS="-static -debug -verbose"
export OPTIONS="-static "

# jakarta.annotation
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.annotation -FileName jakarta.annotation.sig_2.0_se8
# jakarta.security.auth
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.security.auth -FileName jakarta.authentication-api.map
# jakarta.security.jacc
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.security.jacc -FileName jakarta.security.jacc.sig_2.0_se8
# jakarta.batch
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.batch -FileName jakarta.batch.sig_2.0_se8
# jakarta.decorator
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.decorator -FileName jakarta.decorator.sig_2.0_se8
# jakarta.ejb
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.ejb -FileName jakarta.ejb.sig_4.0_se8
# jakarta.el
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.el -FileName jakarta.el.sig_4.0_se8
# jakarta.enterprise
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -Exclude jakarta.enterprise.concurrent -package jakarta.enterprise -FileName jakarta.enterprise.sig_3.0_se8
# jakarta.enterprise.concurrent
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.enterprise.concurrent -FileName jakarta.enterprise.concurrent.sig_2.0_se8
# jakarta.faces
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.faces -FileName jakarta.faces.sig_3.0_se8
# jakarta.inject
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.inject -FileName jakarta.inject.sig_2.0_se8
# jakarta.interceptor
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.interceptor -FileName jakarta.interceptor.sig_2.0_se8
# jakarta.jms
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.jms -FileName jakarta.jms.sig_3.0_se8
# jakarta.json
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -Exclude jakarta.json.bind -package jakarta.json -FileName jakarta.json.sig_2.0_se8
# jakarta.json.bind
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.json.bind -FileName jakarta.json.bind.sig_2.0_se8
# jakarta.mail
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.mail -FileName jakarta.mail.sig_2.0_se8
# jakarta.persistence
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.persistence -FileName jakarta.persistence.sig_3.0_se8
# jakarta.resource
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.resource -FileName jakarta.resource.sig_2.0_se8
# jakarta.security.enterprise
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.security.enterprise -FileName jakarta.security.enterprise.sig_2.0_se8
# jakarta.security.auth.message
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.security.auth.message -FileName jakarta.security.auth.message.sig_2.0_se8
# jakarta.servlet (exclude jakarta.servlet.jsp)
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -Exclude jakarta.servlet.jsp -package jakarta.servlet -FileName jakarta.servlet.sig_5.0_se8
# jakarta.servlet.jsp (exclude jakarta.servlet.jsp.jstl)
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -Exclude jakarta.servlet.jsp.jstl -package jakarta.servlet.jsp -FileName jakarta.servlet.jsp.sig_3.0_se8
# jakarta.servlet.jsp.jstl
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.servlet.jsp.jstl -FileName jakarta.servlet.jsp.jstl.sig_2.0_se8
# jakarta.transaction
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.transaction -FileName jakarta.transaction.sig_2.0_se8
# jakarta.validation
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.validation -FileName jakarta.validation.sig_3.0_se8
# jakarta.websocket
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.websocket -FileName jakarta.websocket.sig_2.0_se8
# jakarta.ws.rs
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.ws.rs -FileName jakarta.ws.rs.sig_3.0_se8
# jakarta.xml.ws
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.xml.ws -FileName jakarta.xml.ws.sig_3.0_se8
# jakarta.xml.soap
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.xml.soap -FileName jakarta.xml.soap.sig_2.0_se8
# javax.rmi (only check on JDK11+)
java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0 -package javax.rmi  -FileName javax.rmi.sig_1.0_se8


# jakarta.jws
# java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0 -package jakarta.jws -FileName webservices-api.map

# jakarta.xml.bind
# java -jar ${TS_HOME}/lib/sigtestdev.jar Setup ${OPTIONS} -classpath ${sigTestClasspath} -apiVersion 1.0  -package jakarta.xml.bind -FileName jakarta.xml.bind-api.map




