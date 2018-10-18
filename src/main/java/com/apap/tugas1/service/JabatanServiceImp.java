package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImp implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
		
	}
	
	@Override
	public List<JabatanModel> getAll() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public void ubahJabatan(JabatanModel jabatan, Long id) {
		JabatanModel newJabatan = jabatanDb.findById(id).get();
		newJabatan.setNama(jabatan.getNama());
		newJabatan.setDeskripsi(jabatan.getDeskripsi());
		newJabatan.setGaji_pokok(jabatan.getGaji_pokok());
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.delete(jabatan);
		
	}

	
}
