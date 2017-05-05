package com.spring.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.spring.model.DraftMail;
import com.spring.model.Patch;
import com.spring.socket.NetasBotUtil;
import com.spring.socket.NetasLDAPUtil;

//import com.spring.model.Test;

@Controller
@RequestMapping(value = "/patch")
public class PatchController {

	/*
	 * @InitBinder public void initBinder(WebDataBinder binder) {
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	 * dateFormat.setLenient(false); binder.registerCustomEditor(Date.class, new
	 * CustomDateEditor(dateFormat, true));
	 * 
	 * }
	 */
	// private static final Object = null;
	@Autowired
	Session session;
	private Long id;

	//@Autowired
	NetasLDAPUtil util;

	//@Autowired
	//@Qualifier("exampleBot")
	NetasBotUtil exampleBot;

	//@Autowired
	NetasLDAPUtil netasLdapUtil;

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update() {
		return "update.html/";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() {
		return "create.html/";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get() {
		return "get.html/";
	}

	@RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	public @ResponseBody Patch name(@PathVariable Long id) {
		ResponseMessage rm = new ResponseMessage();

		try {
			rm.success = true;
			return session.load(Patch.class, id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage name(@RequestBody Patch p) {
		ResponseMessage rm = new ResponseMessage();

		try {

			rm.success = true;
			session.save(p);
			return rm;
		} catch (Exception e) {
			// TODO: handle exception
		}
		rm.success = false;
		return rm;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Collection<Patch> patches() {

		// Patch patches = new Patch();
		// session.save(patches);

		return session.loadAll(Patch.class);

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody void deletePatch(@PathVariable(value = "id") Long id) {

		Patch p = session.load(Patch.class, id);
		// session.delete(Patch.class, id);

		session.delete(p);
	}

	@RequestMapping(value = "/sendmail/{id}", method = RequestMethod.POST)
	public @ResponseBody void sendMailPatch(@PathVariable(value = "id") Long id, @RequestBody DraftMail draftMail) {
		Patch p = session.load(Patch.class, id);

		String[] emails = draftMail.getMails().split(";");
		List<String> listEMails = new ArrayList<>();
		for (String email : emails) {
			if (!email.contains("@")) {
				email +="@netas.com.tr";
			}
			listEMails.add(email);
		}

		String s = draftMail.getBody();
		String[] ss = s.split("\n");
		StringBuilder sb = new StringBuilder();
		for (String string : ss) {
			sb.append(string + "</br>");
		}
		
		util.sendMail(listEMails, draftMail.getSubject(), sb.toString());
		List<String> dripUsers = new ArrayList<>();

		// exampleBot.sendMessage("aoruc", sb.toString());
		for (String email : emails) {
			if (email.contains("netas.com.tr")) {
				dripUsers.add(email.split("@")[0]);
			} else if (!email.contains("@")) {
				dripUsers.addAll(netasLdapUtil.getDistributionLists(email));
			}
		}
		
		for (String user : dripUsers) {
			exampleBot.sendMessage(user, draftMail.getBody());
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		session.save(draftMail);
	}

	@RequestMapping(value = "/mail")
	public String sendMailGet() {
		return "mail.html/";
	}

	@RequestMapping(value = "/mail/draft")
	public @ResponseBody DraftMail draftMail() {
		if (session.countEntitiesOfType(DraftMail.class) > 0) {
			return (DraftMail) session.loadAll(DraftMail.class).toArray()[0];
		}
		return new DraftMail();
	}
}
