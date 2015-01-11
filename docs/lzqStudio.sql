-- phpMyAdmin SQL Dump
-- version 4.3.3
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: 2015-01-04 10:26:58
-- 服务器版本： 5.5.40-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `lzqStudio`
--

-- --------------------------------------------------------

--
-- 表的结构 `article`
--

CREATE TABLE IF NOT EXISTS `article` (
  `artId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `title` varchar(20) COLLATE utf8_bin NOT NULL,
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `content` text COLLATE utf8_bin NOT NULL,
  `preDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `clickNum` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- 转存表中的数据 `article`
--

INSERT INTO `article` (`artId`, `userId`, `title`, `userName`, `content`, `preDate`, `clickNum`) VALUES
(1001001, 1001, '', 'ManDaRen', '我很想', '2015-01-03 15:15:24', 22),
(1001002, 1001, '500', 'ManDaRen', '500 Internal Server Error\n服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。一般来说，这个问题都会在服务器端的源代码出现错误时出现。', '2015-01-03 15:16:03', 3),
(1001003, 1001, '501', 'ManDaRen', '501 Not Implemented\n服务器不支持当前请求所需要的某个功能。当服务器无法识别请求的方法，并且无法支持其对任何资源的请求。', '2015-01-03 15:16:00', 0),
(1001004, 1001, '502', 'ManDaRen', '502 Bad Gateway\n作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。', '2015-01-03 15:15:58', 0),
(1001005, 1001, '503', 'ManDaRen', '503 Service Unavailable\n由于临时的服务器维护或者过载，服务器当前无法处理请求。这个状况是临时的，并且将在一段时间以后恢复。如果能够预计延迟时间，那么响应中可以包含一个 Retry-After 头用以标明这个延迟时间。如果没有给出这个 Retry-After 信息，那么客户端应当以处理500响应的方式处理它。', '2015-01-03 15:15:55', 3),
(1001006, 1001, '504', 'ManDaRen', '504 Gateway Timeout\n作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器（URI标识出的服务器，例如HTTP、FTP、LDAP）或者辅助服务器（例如DNS）收到响应。\n注意：某些代理服务器在DNS查询超时时会返回400或者500错误', '2015-01-03 15:15:52', 7),
(1001007, 1001, '555555555', 'ManDaRen', '莫名', '2015-01-03 16:19:33', 3),
(1001008, 1001, '09时39分48秒', 'ManDaRen', '09:39:5409时39分55秒09时39分56秒炯', '2015-01-04 01:39:59', 0),
(1001009, 1001, '09时39分48秒', 'ManDaRen', '松松秒炯', '2015-01-04 01:44:52', 0),
(1001010, 1001, '09时39分48秒', 'ManDaRen', '松松送s老ass墨点e的 总', '2015-01-04 01:45:03', 0),
(1001011, 1001, '09时39分48秒', 'ManDaRen', '松松送s老ass墨点e的 总', '2015-01-04 01:45:05', 0),
(1001012, 1001, '09时39分48秒', 'ManDaRen', '松松送s老ass墨点e的 总asdassddaca cd cd ccdd', '2015-01-04 01:45:16', 0),
(1001013, 1001, 'sd', 'ManDaRen', '&lt;body&gt;', '2015-01-04 01:48:22', 0),
(1001014, 1001, 'sd', 'ManDaRen', '&lt;body&gt;\n&lt;.fgf', '2015-01-04 01:48:30', 0),
(1001015, 1001, 'sd', 'ManDaRen', '&lt;body&gt;\n&lt;.fgf/&gt;', '2015-01-04 01:48:33', 0),
(1001016, 1001, 'sd', 'ManDaRen', '&lt;body&gt;\n&lt;/body&gt;', '2015-01-04 01:48:41', 0),
(1001017, 1001, 'sd', 'ManDaRen', '&lt;body&gt;\n              as d\n&lt;/body&gt;', '2015-01-04 01:48:52', 0),
(1001018, 1001, 'ssss', 'ManDaRen', '&lt;body&gt;nn&lt;/html&gt;', '2015-01-04 02:01:41', 0),
(1001019, 1001, 'ssss', 'ManDaRen', '&lt;body&gt;n&lt;td&gt;n&lt;button n', '2015-01-04 02:03:18', 0),
(1001020, 1001, 'ssss', 'ManDaRen', '&lt;body&gt;n&lt;td&gt;n&lt;button &gt;n', '2015-01-04 02:03:21', 0),
(1001021, 1001, 'ssss', 'ManDaRen', '&lt;body&gt;n&lt;td&gt;n&lt;buttotypn', '2015-01-04 02:03:52', 0);

-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `commentId` int(14) NOT NULL,
  `articleId` int(11) DEFAULT NULL,
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `imageUrl` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `content` text COLLATE utf8_bin NOT NULL,
  `preTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- 转存表中的数据 `comment`
--

INSERT INTO `comment` (`commentId`, `articleId`, `userName`, `imageUrl`, `content`, `preTime`) VALUES
(1001001001, 1001001, 'ManDaRen', '/upload', '你的想法不错', '2014-11-09 00:17:32'),
(1001002001, 1001002, 'ManDaRen', NULL, '大人,你讲', '2014-11-11 16:00:00'),
(1001001002, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 08:29:36'),
(1001001003, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 08:29:37'),
(1001001004, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 08:29:38'),
(1001001005, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 08:29:45'),
(1001001006, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 08:59:48'),
(1001001007, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 09:00:56'),
(1001001008, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 09:00:57'),
(1001001009, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 09:00:57'),
(1001001010, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 09:01:26'),
(1001001011, 1001001, 'ManDaRen', NULL, '今天心情不错', '2014-11-09 09:02:02');

-- --------------------------------------------------------

--
-- 表的结构 `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `imageId` int(11) NOT NULL,
  `imageUrl` varchar(100) COLLATE utf8_bin NOT NULL,
  `imageName` varchar(20) COLLATE utf8_bin NOT NULL,
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `preDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `message`
--

CREATE TABLE IF NOT EXISTS `message` (
  `messageId` int(11) NOT NULL,
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `content` varchar(100) COLLATE utf8_bin NOT NULL,
  `preDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- 表的结构 `userinfo`
--

CREATE TABLE IF NOT EXISTS `userinfo` (
  `userId` int(11) NOT NULL,
  `userName` varchar(20) COLLATE utf8_bin NOT NULL,
  `userPass` varchar(20) COLLATE utf8_bin NOT NULL,
  `userSex` enum('man','woman','') COLLATE utf8_bin NOT NULL,
  `userMood` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `userMail` varchar(20) COLLATE utf8_bin NOT NULL,
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userIurl` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- 转存表中的数据 `userinfo`
--

INSERT INTO `userinfo` (`userId`, `userName`, `userPass`, `userSex`, `userMood`, `userMail`, `regDate`, `userIurl`) VALUES
(1001, 'ManDaRen', 'ainnnn', 'woman', '', '', '2015-01-03 15:14:39', 'www');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`artId`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`commentId`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`imageId`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`messageId`);

--
-- Indexes for table `userinfo`
--
ALTER TABLE `userinfo`
  ADD PRIMARY KEY (`userId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
