package cn.luminous.squab.realm;

import cn.luminous.squab.entity.SysUer;
import cn.luminous.squab.service.SysUserService;
import cn.luminous.squab.util.MD5Util;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从凭证中获得用户名
        //String userCode = (String) SecurityUtils.getSubject().getPrincipal();
        return null;
    }


    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获得当前用户的用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String pwd = new String(token.getPassword());
        SysUer sysUer = new SysUer();
        sysUer.setName(token.getUsername());
        SysUer sysUerInDB = sysUserService.queryOne(sysUer);
        if (sysUerInDB==null) throw new ShiroException("用户名不存在");
        if (!MD5Util.MD5(pwd, "utf-8").equals(sysUerInDB.getPassword())) {
            throw new ShiroException("密码错误");
        }else {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), pwd,getName());
            return info;
        }
    }
}
