package com.sec.service;

import java.util.List;

import com.sec.exception.MBCBaseException;

public interface IBaseService<T> {

	T saveData(T t) throws MBCBaseException;

	T updateData(T t) throws MBCBaseException;

	void deleteDataById(long id) throws MBCBaseException;

	T getDataById(long id) throws MBCBaseException;

	List<T> getAllDatas() throws MBCBaseException;
}