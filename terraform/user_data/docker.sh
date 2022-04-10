#! /bin/sh
yum update -y
amazon-linux-extras install docker
service docker start
usermod -a -G docker ec2-user
chkconfig docker on
docker pull kurchakkhrystyna/broken-hammer:latest
docker run -d --name broken-hammer-app -p 80:8080 kurchakkhrystyna/broken-hammer:latest