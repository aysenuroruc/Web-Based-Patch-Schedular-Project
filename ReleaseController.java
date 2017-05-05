package com.spring.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.ResponseMessage;
import com.spring.model.Release;
import com.spring.socket.NetasBotUtil;
import com.spring.socket.NetasLDAPUtil;

@Controller
@RequestMapping(value = "/release")
public class ReleaseController {
	@Autowired
	Session session;
	
	@Autowired
	NetasLDAPUtil util;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String name() {
		return "release.html/";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editRelease() {
		return "edit.html/";
	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage name(@RequestBody Release p) {
		ResponseMessage rm = new ResponseMessage();
		
		try {
			rm.success=true;
			session.save(p);
			session.clear();
			return rm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		rm.success=false;
		
		return rm;
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Collection<Release> releases() {
		
		//Patch patches = new Patch();
	  //  patches.getName();
		//session.save(patches);
		
		return session.loadAll(Release.class);
	}
	
	 @RequestMapping(value = "/delete/{id}" , method = RequestMethod.GET)
	   public @ResponseBody void deletePatch(@PathVariable(value="id") Long id){
		 Release p = session.load(Release.class, id);
		 Map<String, Object> parameters = new HashMap<>();
		 parameters.put("rid", id);
			
		 session.query("MATCH (n:Patch),((n)<-[:PATCHES]-(r:Release)) WHERE ID(r)={rid} DETACH DELETE n;", parameters);
		 
	    // session.delete(Patch.class, id);
		  session.delete(p);
	     }
}
