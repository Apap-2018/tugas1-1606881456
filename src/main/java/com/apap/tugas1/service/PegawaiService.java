package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> getAllPegawai();
	PegawaiModel getPegawaiTertua(InstansiModel instansi);
	PegawaiModel getPegawaiTermuda(InstansiModel instansi);
	String generateNip(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai,String nip);
	}
