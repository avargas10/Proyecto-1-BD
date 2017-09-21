/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2016 (13.0.4001)
    Source Database Engine Edition : Microsoft SQL Server Express Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2017
    Target Database Engine Edition : Microsoft SQL Server Standard Edition
    Target Database Engine Type : Standalone SQL Server
*/
USE [master]
GO
/****** Object:  Database [AndroidAppDB]    Script Date: 20/09/2017 23:25:36 ******/
CREATE DATABASE [AndroidAppDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'AndroidAppDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.SQLEXPRESS\MSSQL\DATA\AndroidAppDB.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'AndroidAppDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.SQLEXPRESS\MSSQL\DATA\AndroidAppDB_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [AndroidAppDB] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [AndroidAppDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [AndroidAppDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [AndroidAppDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [AndroidAppDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [AndroidAppDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [AndroidAppDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [AndroidAppDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [AndroidAppDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [AndroidAppDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [AndroidAppDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [AndroidAppDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [AndroidAppDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [AndroidAppDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [AndroidAppDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [AndroidAppDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [AndroidAppDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [AndroidAppDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [AndroidAppDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [AndroidAppDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [AndroidAppDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [AndroidAppDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [AndroidAppDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [AndroidAppDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [AndroidAppDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [AndroidAppDB] SET  MULTI_USER 
GO
ALTER DATABASE [AndroidAppDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [AndroidAppDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [AndroidAppDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [AndroidAppDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [AndroidAppDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [AndroidAppDB] SET QUERY_STORE = OFF
GO
USE [AndroidAppDB]
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
ALTER DATABASE [AndroidAppDB] SET  READ_WRITE 
GO
