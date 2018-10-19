package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.GET)
	private String addJabatan(Model model) {
		JabatanModel jabatan = new JabatanModel();
		model.addAttribute("jabatan",jabatan);
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String submitAddJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "submit-add";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam(value="idJabatan") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id).get();
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.GET)
	public String ubahJabatan(@RequestParam(value="idJabatan") String idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(idJabatan)).get();
		System.out.println("JABATAN : " + jabatan.getNama());
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	private String submitUbahJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.ubahJabatan(jabatan, jabatan.getId());
		model.addAttribute("jabatan", jabatan);
		return "suksesUbah";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	private String deleteJabatan(@RequestParam(value="idJabatan") String idJabatan, Model model) {
		JabatanModel jabatanPeg = jabatanService.getJabatanById(Long.parseLong(idJabatan)).get();
		System.out.println("JABATAN : " + jabatanPeg.getNama());
		jabatanService.deleteJabatan(jabatanPeg);
		return "delete";
	}
	
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	private String viewAllJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		List<JabatanModel> jabatanList = jabatanService.getAll();
		model.addAttribute("jabatanList", jabatanList);
		return "view-jabatanAll";
	}
}
