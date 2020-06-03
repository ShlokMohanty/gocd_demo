#!/bin/bash

#check if container exist with given name, delete it
name=$1

container_id=`docker ps -aqf name=$name`
echo $container_id
if [ -z $container_id ];
then
	echo "Old Application does not exist"
else
	docker rm $name -f
fi