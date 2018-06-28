package zhangchx.spring.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zhangchx.spring.bean.User;
import zhangchx.spring.service.IUserMgrServcie;

@Controller
@RequestMapping("/user")
public class ActionUserMgr {
	
	@Autowired
	IUserMgrServcie usermgr;
	
	@RequestMapping(method = RequestMethod.GET)
	public void list() {
		usermgr.list();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void query(@PathVariable String id) {
		usermgr.query(id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(String id) {
		usermgr.delete(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void add(User user) {
		usermgr.add(user);
	}
	
//	@RequestMapping(method = RequestMethod.PATCH)
	public void update(String id, User user) {
		usermgr.update(id, user);
	}
}
