package com.apap.tugas1.service;

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
	
}
