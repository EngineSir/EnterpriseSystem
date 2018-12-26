package io.dtchain.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.dtchain.entity.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.dtchain.dao.MangageDao;
import io.dtchain.entity.EmpInfo;
import io.dtchain.service.ResourceService;

public class ShiroRealm extends AuthorizingRealm  {

	@Autowired
	private MangageDao mangageDao;
	@Autowired
	private ResourceService resourceService;
	//获取授权信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username= (String)SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> map = new HashMap<String,Object>();
        EmpInfo user=mangageDao.queryInfo(username);
        map.put("userid",user.getEmpId());
        List<Resource> resourcesList = resourceService.loadUserResources(map);
        System.out.println("resourcesList:"+resourcesList);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Resource resources: resourcesList){
            info.addStringPermission(resources.getResUrl());
        }
        return info;
	}

	//获取验证信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取用户的输入的账号.
        String username = (String)token.getPrincipal();
       
        EmpInfo user=mangageDao.queryInfo(username);
        if(user==null) throw new UnknownAccountException();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getEmpName(), //用户名  使用shiro:principal=""可以获取到改值SecurityUtils.getSubject().getPrincipal()可以获取到改值
                user.getEmpPass(), //密码
                ByteSource.Util.bytes(username),
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getEmpId());
        return authenticationInfo;
	}

}
