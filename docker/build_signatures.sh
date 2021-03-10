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
# 
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$PATH

if [ -z "$WORKSPACE" ]; then
  export WORKSPACE=`pwd`
fi

cd $WORKSPACE
export BASEDIR=`pwd`

if [ -z "$JAKARTA_JARS" ]; then
  export JAKARTA_JARS=$BASEDIR
fi

echo "JAKARTA_JARS = $JAKARTA_JARS"
export TS_HOME=$BASEDIR

which java
java -version

cd $BASEDIR

mkdir -p $JAKARTA_JARS/modules
mkdir -p $JAKARTA_JARS/endorsed
cd $JAKARTA_JARS/modules
mvn -f $BASEDIR/docker/pom.xml dependency:copy-dependencies -DoutputDirectory="${JAKARTA_JARS}/modules" -Dmdep.stripVersion=true

echo "generate TCK signature map files"
cd $JAKARTA_JARS/lib
# replace existing (https://wiki.openjdk.java.net/display/CodeTools/sigtest) sigtest.jar with latest jar from (fork) https://github.com/jtulach/netbeans-apitest
mvn dependency:get -Dartifact=org.netbeans.tools:sigtest-maven-plugin:LATEST:jar -Dtransitive=false -Ddest=sigtest.jar
export sigtest_classes="${JAKARTA_JARS}/lib/sigtest.jar"
export pathsep=:
export deliverabledir=signature-repository
mkdir -p $BASEDIR/install/$deliverabledir
export tckname=jakartaee
export TS_HOME=$JAKARTA_JARS

cd $JAKARTA_JARS/src/com/sun/ts/tests/signaturetest
for tckjarname in $JAKARTA_JARS/modules/jakarta*.jar; do
  [ -e "$tckjarname" ] || continue
  # generate signature for each jakarta*.jar 
  echo "generate signature for $tckjarname"
  sigTestClasspath=${pathsep}${tckjarname}${pathsep}${JAVA_HOME}/jre/lib/rt.jar${pathsep}
  # ant -find record-build.xml -Dsig.source="${sigtest_classes}${pathsep}${sigTestClasspath}" -Dmap.file=${TS_HOME}/install/jakartaee/bin/sig-test_se8.map -Ddeliverabledir="modules"   -Drecorder.type=sigtest record.sig.batch 
  java -jar ${TS_HOME}/lib/sigtestdev.jar Setup -classpath ${sigTestClasspath} -FileName $tckjarname.map -apiVersion 1.0 -package jakarta
done

#TODO : security jar is required to compile SunRILoginContext.java, look for alternative way to remove this dependency
wget --progress=bar:force --no-cache https://repo1.maven.org/maven2/org/glassfish/security/security/3.1.1/security-3.1.1.jar -O ${JAKARTA_JARS}/modules/security.jar


