
Source Server         : 172.16.16.36
Source Server Version : 50627
Source Host           : 172.16.16.36:3306
Source Database       : ixinnuo_financial_v2

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2017-09-11 18:31:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  `product_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
  `guarantee_type_code` varchar(20) DEFAULT NULL COMMENT '担保方式代码',
  `guarantee_type_name` varchar(20) DEFAULT NULL COMMENT '担保方式名称',
  `max_loan_amount` double DEFAULT NULL COMMENT '最大贷款额度',
  `repayment_code` varchar(20) DEFAULT NULL COMMENT '还款方式代码',
  `repayment_name` varchar(20) DEFAULT NULL COMMENT '还款方式名称',
  `repayment_term` varchar(40) DEFAULT NULL COMMENT '还款期限',
  `rate` varchar(40) DEFAULT NULL COMMENT '利率',
  `product_remark` varchar(200) DEFAULT NULL COMMENT '产品备注',
  `status_code` varchar(20) DEFAULT NULL COMMENT '产品状态代码',
  `status_name` varchar(20) DEFAULT NULL COMMENT '产品状态名称',
  `bank_code` varchar(20) DEFAULT NULL COMMENT '银行简称',
  `bank_name` varchar(128) DEFAULT NULL COMMENT '银行名称',
  `file_path` varchar(100) DEFAULT NULL COMMENT '填写表单的相对url路径',
  `access_template_id` int(11) DEFAULT NULL COMMENT '准入计算模板ID',
  `product_detail` mediumtext COMMENT '产品详情',
  `apply_materials` mediumtext COMMENT '申请资料',
  `loan_agreement` mediumtext COMMENT '贷款协议',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_test_hello
-- ----------------------------
DROP TABLE IF EXISTS `t_test_hello`;
CREATE TABLE `t_test_hello` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `money` decimal(15,2) DEFAULT NULL,
  `simp_describe` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;