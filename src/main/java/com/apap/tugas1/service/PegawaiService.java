package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> getAllPegawai();
	PegawaiModel getPegawaiTertua(InstansiModel instansi);
	PegawaiModel getPegawaiTermuda(InstansiModel instansi);
	String generateNip(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel oldPegawai, PegawaiModel newPegawai);
	List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> findPegawaiByProvinsiAndJabatan(List<PegawaiModel> pegawaiProvinsi,JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	}
