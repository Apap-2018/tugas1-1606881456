package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String index(Model model) {
		List<JabatanModel> jabatan = jabatanService.getAll();
        model.addAttribute("listJabatan", jabatan);
        List<InstansiModel> instansi = instansiService.getAllInstansi();
        model.addAttribute("listInstansi", instansi);
		return "index";
	}

	@RequestMapping(value="/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model ) {
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
	
	@RequestMapping(value="/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewPegawaiMudaTua(@RequestParam("idInstansi") Long id, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(id);
		PegawaiModel pegawaiMuda = pegawaiService.getPegawaiTermuda(instansi);
		PegawaiModel pegawaiTertua = pegawaiService.getPegawaiTertua(instansi);
		
		List<JabatanPegawaiModel> jabatanMuda = jabatanPegawaiService.getJabatanByNip(pegawaiMuda.getNip()).get();
		List<JabatanPegawaiModel> jabatanTua = jabatanPegawaiService.getJabatanByNip(pegawaiTertua.getNip()).get();
		model.addAttribute("pegawaiMuda", pegawaiMuda);
		model.addAttribute("pegawaiTua", pegawaiTertua);
		model.addAttribute("jabatanMuda", jabatanMuda);
		model.addAttribute("jabatanTua",jabatanTua);
		return "view-tua-muda";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanPegawaiModel>());
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatan().add(jabatanPegawai);
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		ProvinsiModel provPertama = daftarProvinsi.get(0);
		List<InstansiModel> daftarInstansi = provPertama.getListInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAll();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("daftarJabatan",daftarJabatan);
		model.addAttribute("daftarInstansi",daftarInstansi);
		model.addAttribute("daftarProvinsi", daftarProvinsi);
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST, params= {"addJabatan"})
	private String addRowJabatan(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel pegawaiBaru = pegawai;
		JabatanPegawaiModel jabatan = new JabatanPegawaiModel();
		jabatan.setPegawai(pegawaiBaru);
		pegawaiBaru.getListJabatan().add(jabatan);
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> daftarInstansi = new ArrayList<InstansiModel>();
		daftarInstansi = daftarProvinsi.get(0).getListInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAll();
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("daftarInstansi", daftarInstansi);
		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("daftarJabatan", daftarJabatan);
		return "add-pegawai";
	}
	
	@RequestMapping(value="pegawai/tambah", method=RequestMethod.POST, params= {"save"})
	private String submitPegawai(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatanPegawai = pegawai.getListJabatan();
		pegawai.setListJabatan(new ArrayList<JabatanPegawaiModel>());
		pegawaiService.addPegawai(pegawai);
		
		for(JabatanPegawaiModel jabatanpegawai : listJabatanPegawai) {
			jabatanpegawai.setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(jabatanpegawai);
		}
		model.addAttribute("tambahPegawai", "Pegawai");
		return "submit-add";
	}
	
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "submit-add";
		
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	private String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> daftarInstansi = instansiService.getAllInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAll();
		model.addAttribute("daftarJabatan", daftarJabatan);
		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("daftarInstansi", daftarInstansi);
		if (pegawai != null) {
			model.addAttribute("pegawai", pegawai);
			return "update-pegawai";
		}
		return "not-found";
	}
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST, params= {"addJabatan"})
	private String addRowUpdateJabatan(@ModelAttribute PegawaiModel pegawai, Model model) {
		JabatanPegawaiModel jabatan = new JabatanPegawaiModel();
		jabatan.setPegawai(pegawai);
		pegawai.getListJabatan().add(jabatan);
		List<ProvinsiModel> daftarProvinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> daftarInstansi = new ArrayList<InstansiModel>();
		daftarInstansi = daftarProvinsi.get(0).getListInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getAll();
		model.addAttribute("daftarJabatan", daftarJabatan);
		model.addAttribute("daftarInstansi",daftarInstansi);
		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}
	
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String submitUbahPegawai(@ModelAttribute PegawaiModel newPegawai) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(newPegawai.getNip());
		String nip = pegawaiService.generateNip(newPegawai);
		newPegawai.setNip(nip);
		pegawaiService.updatePegawai(newPegawai, pegawai.getNip());
		return "suksesUbah";
	}
	
	@RequestMapping(value="/pegawai/cekInstansi", method=RequestMethod.GET)
	private @ResponseBody List<InstansiModel> findAllInstansi(@RequestParam(value="provinsiId", required=true) Long provinsiId){
		ProvinsiModel provinsi = provinsiService.getProvinsiById(provinsiId);
		List<InstansiModel> daftarInstansi = instansiService.getInstansiByProvinsi(provinsi);
		return daftarInstansi;
	}
}
