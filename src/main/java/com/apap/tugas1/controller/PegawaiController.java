package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;


@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String index(Model model) {
		List<JabatanModel> jabatan = jabatanService.getAll();
        model.addAttribute("listJabatan", jabatan);
		return "index";
	}

	@RequestMapping(value="/pegawai", method = RequestMethod.GET)
	private String viewPilot(@RequestParam("nip") String nip, Model model ) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		List<JabatanPegawaiModel> jabatan = jabatanPegawaiService.getJabatanByNip(nip).get();
		//ngitung gaji
		double gajiPegawai = 0.0;
		for(JabatanPegawaiModel jbtn : jabatan) {
			if(jbtn.getJabatan().getGaji_pokok()>gajiPegawai) {
				gajiPegawai+=jbtn.getJabatan().getGaji_pokok();
			}
			
		}
		gajiPegawai+=((pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()*0.01)*gajiPegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanPegawai", jabatan);
		model.addAttribute("gajiPegawai", (long)gajiPegawai);
		return "view-pegawai";
	}
	
}
