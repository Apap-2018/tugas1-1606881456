package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;


@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public List<PegawaiModel> getAllPegawai() {
		// TODO Auto-generated method stub
		return pegawaiDb.findAll();
	}

	@Override
	public PegawaiModel getPegawaiTertua(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> listPegawai = pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return listPegawai.get(0);
	}

	@Override
	public PegawaiModel getPegawaiTermuda(InstansiModel instansi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> listPegawai = pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return listPegawai.get(listPegawai.size()-1);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		String nip ="";
		nip += pegawai.getInstansi().getId();
		Date tgl = pegawai.getTanggalLahir();
		String[] tglLahir =(""+tgl).split("-");
		for(int i = tglLahir.length-1; i>=0; i--) {
			nip+=tglLahir[i].substring(tglLahir[i].length()-2, tglLahir[i].length());
		}
		nip+=pegawai.getTahunMasuk();
		List<PegawaiModel> listPegawai = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(tgl, pegawai.getTahunMasuk(), pegawai.getInstansi());
		if(listPegawai.size()<10) {
			nip+="0"+listPegawai.size();
		}
		else {
			nip+=listPegawai.size();
		}
		pegawai.setNip(nip);
		return nip;
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai,String nip) {
		// TODO Auto-generated method stub
		PegawaiModel newPegawai = pegawaiDb.findByNip(nip);
		newPegawai.setNama(pegawai.getNama());
		newPegawai.setNip(pegawai.getNip());
		newPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		newPegawai.setTanggalLahir(pegawai.getTanggalLahir());
		newPegawai.setTempatLahir(pegawai.getTempatLahir());
		newPegawai.setInstansi(pegawai.getInstansi());
		newPegawai.setListJabatan(pegawai.getListJabatan());
		pegawaiDb.save(newPegawai);
	}
	
}
