sudo docker run -d --name mysql-server -p 3306:3306 --net=host -v ~/mysql-server/datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=rootpwd123 mysql
