#!/bin/bash

root_folder=$(cd $(dirname $0); pwd)
openliberty_target="target/liberty/wlp/usr"

cp -r $root_folder/liberty-opentracing-zipkintracer $root_folder/$openliberty_target
