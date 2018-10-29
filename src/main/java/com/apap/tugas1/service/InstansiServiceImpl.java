package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public List<InstansiModel> getAllInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel findInstansiById(long id) {
		// TODO Auto-generated method stub
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> getAll() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		List<InstansiModel> allInstansi = instansiDb.findAll();
		List<InstansiModel> allInstansiPerProvinsi = new ArrayList<InstansiModel>();
		for(InstansiModel instansi : allInstansi) {
			if(instansi.getProvinsi().equals(provinsi)) {
				allInstansiPerProvinsi.add(instansi);
			}
		}
		return allInstansiPerProvinsi;
	}

}
