-- MYSQL
-- 创建用户
CREATE USER 'username'@'host' IDENTIFIED BY 'password';
/**
    说明：username - 将创建的用户名，
         host-指定该用户在哪个主机上可以登录，如果是本地用户可用localhost，如果想让该用户可以从任意远程主机登录，可以使用通配符'%'。
         password - 该用户的登陆密码,密码可以为空,如果为空则该用户可以不需要密码登陆服务器。
 */
CREATE USER 'username'@'%' IDENTIFIED BY 'password';

-- 刷新权限
flush privileges;

-- 授权
/**
    GRANT privileges ON databasename.tablename TO 'username'@'host';
    说明：privileges - 用户的操作权限，如SELECT，INSERT，UPDATE等，如果授予所有的权限则使用ALL。
         databasename - 数据库名。
         tablename - 表名，如果要授予该用户对所有数据库和表的相应操作权限则可用'*'表示，如*.*。
    授予用户权限的SQL命令：
    GRANT ALL ON *.* TO 'username'@'%'; -- 给用户username授权所有权限
    GRANT SELECT,INSERT ON *.* TO 'username'@'%'; -- 给用户授予查询和插入权限
    GRANT privileges ON databasename.tablename TO 'username'@'host' WITH GRANTOPTION; -- 给予用户授权权
 */
GRANT ALL ON *.* TO 'username'@'%';

-- 更改密码
SET PASSWORD FOR 'username'@'%' = 'password';
SET PASSWORD = 'password'; -- 当前登录用户修改密码

-- 撤销用户权限
REVOKE ALL ON *.* FROM 'username'@'%';

-- 删除用户
DROP USER 'username'@'%';