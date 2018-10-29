package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	List<InstansiModel> getAllInstansi();
	InstansiModel findInstansiById(long id);
	List<InstansiModel> getAll();
	List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi);
}
