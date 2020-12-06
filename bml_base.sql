/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.19 : Database - bml_base
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bml_base` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bml_base`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名 不重复',
  `password` varchar(256) DEFAULT NULL COMMENT '加密后的密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`create_time`,`update_time`) values (1,'123456','6c195211e3dbe9f27e912c008b569530','2020-03-22 12:49:20','2020-05-24 10:08:05'),(2,'654321','f6b324f612b487dbf33f4e44b6130a15','2020-03-22 12:43:42','2020-05-21 16:31:35'),(3,'111111','fa422eb559f0021399133f945d2143fd','2020-03-30 15:50:41','2020-05-18 10:16:42'),(4,'222222','b1e41ddb79c63276e7b517e9dfe963aa','2020-03-30 15:51:04','2020-03-31 21:34:20'),(5,'333333','1b4f16be93570a5df8dc82138c580291','2020-03-30 15:51:20','2020-03-31 21:10:12'),(6,'444444','e4478e5583110c90db93627de26d9dfd','2020-03-30 15:51:33','2020-03-30 15:51:33'),(9,'555555','f34d16aebc0e342b565fb08ceb9d8df3','2020-03-30 20:03:27','2020-04-16 15:25:47');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
