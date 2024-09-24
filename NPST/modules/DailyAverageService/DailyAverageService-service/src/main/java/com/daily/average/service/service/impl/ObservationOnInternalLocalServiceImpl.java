/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.daily.average.service.service.impl;


import com.daily.average.service.model.ObservationOnInternal;
import com.daily.average.service.service.ObservationOnInternalLocalServiceUtil;
import com.daily.average.service.service.base.ObservationOnInternalLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ObservationOnInternalLocalServiceImpl
	extends ObservationOnInternalLocalServiceBaseImpl {
	
	/**
	 * 
	 * @param observations_i_a
	 * @param management_i_a
	 * @param remarks_i_a
	 * @param observations_i_b
	 * @param management_i_b
	 * @param remarks_i_b
	 * @param observations_i_c
	 * @param management_i_c
	 * @param remarks_i_c
	 * @param observations_i_d
	 * @param management_i_d
	 * @param remarks_i_d
	 * @param observations_ii_a
	 * @param management_ii_a
	 * @param remarks_ii_a
	 * @param observations_ii_b
	 * @param management_ii_b
	 * @param remarks_ii_b
	 * @param observations_ii_c
	 * @param management_ii_c
	 * @param remarks_ii_c
	 * @param observations_ii_d
	 * @param management_ii_d
	 * @param remarks_ii_d
	 * @param observations_iii_a
	 * @param management_iii_a
	 * @param remarks_iii_a
	 * @param management_iii_b
	 * @param remarks_iii_b
	 * @param observations_iii_b
	 * @param observations_iii_c
	 * @param management_iii_c
	 * @param remarks_iii_d
	 * @param observations_iii_d
	 * @param management_iii_d
	 * @param observations_iii_e
	 * @param management_iii_e
	 * @param remarks_iii_e
	 * @param observations_iii_f
	 * @param management_iii_f
	 * @param remarks_iii_f
	 * @param observations_iii_g
	 * @param management_iii_g
	 * @param remarks_iii_g
	 * @param observations_iii_h
	 * @param management_iii_h
	 * @param remarks_iii_h
	 * @param observations_iii_i
	 * @param management_iii_i
	 * @param remarks_iii_i
	 * @param observations_iv_a
	 * @param management_iv_a
	 * @param remarks_iv_a
	 * @param observations_iv_b
	 * @param management_iv_b
	 * @param remarks_iv_b
	 * @param observations_iv_c
	 * @param management_iv_c
	 * @param remarks_iv_c
	 * @param observations_iv_d
	 * @param management_iv_d
	 * @param remarks_iv_d
	 * @param observations_v_a
	 * @param management_v_a
	 * @param remarks_v_a
	 * @param observations_v_b
	 * @param management_v_b
	 * @param remarks_v_b
	 * @param observations_v_c
	 * @param management_v_c
	 * @param remarks_v_c
	 * @param observations_v_d
	 * @param management_v_d
	 * @param remarks_v_d
	 * @param observations_vi_a
	 * @param management_vi_a
	 * @param remarks_vi_a
	 * @param observations_vi_b
	 * @param management_vi_b
	 * @param remarks_vi_b
	 * @param observations_vi_c
	 * @param management_vi_c
	 * @param remarks_vi_c
	 * @param observations_vi_d
	 * @param management_vi_d
	 * @param remarks_vi_d
	 * @param observations_vi_e
	 * @param management_vi_e
	 * @param remarks_vi_e
	 * @param observations_vii_a
	 * @param management_vii_a
	 * @param remarks_vii_a
	 * @param observations_vii_b
	 * @param management_vii_b
	 * @param remarks_vii_b
	 * @param observations_vii_c
	 * @param management_vii_c
	 * @param remarks_vii_c
	 * @param observations_vii_d
	 * @param management_vii_d
	 * @param remarks_vii_d
	 * @param observations_vii_e
	 * @param management_vii_e
	 * @param remarks_vii_e
	 * @param observations_vii_f
	 * @param management_vii_f
	 * @param remarks_vii_f
	 * @param observations_vii_g
	 * @param management_vii_g
	 * @param remarks_vii_g
	 * @param observations_viii_a
	 * @param management_viii_a
	 * @param remarks_viii_a
	 * @param management_ix_a
	 * @param observations_ix_a
	 * @param remarks_ix_a
	 * @param observations_ix_b
	 * @param management_ix_b
	 * @param remarks_ix_b
	 * @param observations_ix_c
	 * @param management_ix_c
	 * @param remarks_ix_c
	 * @param observations_ix_d
	 * @param management_ix_d
	 * @param remarks_ix_d
	 * @param observations_x_a
	 * @param management_x_a
	 * @param remarks_x_a
	 * @param observations_x_b
	 * @param management_x_b
	 * @param remarks_x_b
	 * @param observations_x_c
	 * @param management_x_c
	 * @param remarks_x_c
	 * @param observations_x_d
	 * @param management_x_d
	 * @param remarks_x_d
	 * @param observations_x_e
	 * @param management_x_e
	 * @param remarks_x_e
	 * @param observations_x_f
	 * @param management_x_f
	 * @param remarks_x_f
	 * @param observations_x_g
	 * @param management_x_g
	 * @param remarks_x_g
	 * @param observations_xi_a
	 * @param management_xi_a
	 * @param remarks_xi_a
	 * @param observations_xi_b
	 * @param management_xi_b
	 * @param remarks_xi_b
	 * @param observations_xi_c
	 * @param management_xi_c
	 * @param remarks_xi_c
	 * @param observations_xi_d
	 * @param management_xi_d
	 * @param remarks_xi_d
	 * @param observations_xii_a
	 * @param management_xii_a
	 * @param remarks_xii_a
	 * @param observations_xii_b
	 * @param management_xii_b
	 * @param remarks_xii_b
	 * @param observations_xii_c
	 * @param management_xii_c
	 * @param remarks_xii_c
	 * @param observations_xii_d
	 * @param management_xii_d
	 * @param remarks_xii_d
	 * @param observations_xii_e
	 * @param management_xii_e
	 * @param remarks_xii_e
	 * @param observations_xiii_a
	 * @param management_xiii_a
	 * @param remarks_xiii_a
	 * @param observations_xiii_b
	 * @param management_xiii_b
	 * @param remarks_xiii_b
	 * @param observations_xiii_c
	 * @param management_xiii_c
	 * @param remarks_xiii_c
	 * @param observations_xiii_d
	 * @param management_xiii_d
	 * @param remarks_xiii_d
	 * @param observation_xiv__a
	 * @param management_xiv_a
	 * @param remarks_xiv_a
	 * @param observations_xiv_b
	 * @param management_xiv_b
	 * @param remarks_xiv_b
	 * @param observations_xiv_c
	 * @param management_xiv_c
	 * @param remarks_xiv_c
	 * @param observations_xiv_d
	 * @param management_xiv_d
	 * @param remarks_xiv_d
	 * @param observations_xiv_e
	 * @param management_xiv_e
	 * @param remarks_xiv_e
	 * @param observations_xiv_f
	 * @param management_xiv_f
	 * @param remarks_xiv_f
	 * @param observations_xiv_g
	 * @param management_xiv_g
	 * @param remarks_xiv_g
	 * @param observations_xv_a
	 * @param management_xv_a
	 * @param remarks_xv_a
	 * @param observations_xv_b
	 * @param management_xv_b
	 * @param remarks_xv_b
	 * @param observations_xv_c
	 * @param management_xv_c
	 * @param remarks_xv_c
	 * @param observations_xvi_a
	 * @param management_xvi_a
	 * @param remarks_xvi_a
	 * @param observations_xvi_b
	 * @param management_xvi_b
	 * @param remarks_xvi_b
	 * @param observations_xvii_a
	 * @param management_xvii_a
	 * @param remarks_xvii_a
	 * @param observations_xvii_b
	 * @param management_xvii_b
	 * @param observations_xvii_c
	 * @param remarks_xvii_b
	 * @param management_xvii_c
	 * @param remarks_xvii_c
	 * @param observations_xvii_d
	 * @param management_xvii_d
	 * @param remarks_xvii_d
	 * @param observations_xviii_a
	 * @param management_xviii_a
	 * @param remarks_xviii_a
	 * @param observations_xviii_b
	 * @param management_xviii_b
	 * @param remarks_xviii_b
	 * @param observations_xviii_c
	 * @param management_xviii_c
	 * @param observations_xviii_d
	 * @param remarks_xviii_c
	 * @param management_xviii_d
	 * @param observations_xix_a
	 * @param management_xix_a
	 * @param remarks_xix_a
	 * @param remarks_xviii_d
	 * @param createdby
	 * @param filenoi 
	 * @param datei 
	 * @param namei 
	 * @param adressi 
	 * @param subject 
	 * @param dateii 
	 * @param dateiii 
	 * @return
	 */
	
	/*public ObservationOnInternal addObservationOnInternal(String observations_i_a, String management_i_a, String remarks_i_a, String observations_i_b, String management_i_b, String remarks_i_b, String observations_i_c, String management_i_c, String remarks_i_c, String observations_i_d, String management_i_d, String remarks_i_d, String observations_ii_a, String management_ii_a, String remarks_ii_a, String observations_ii_b, String management_ii_b, String remarks_ii_b, String observations_ii_c, String management_ii_c, String remarks_ii_c, String observations_ii_d, String management_ii_d, String remarks_ii_d, String observations_iii_a, String management_iii_a, String remarks_iii_a, String management_iii_b, String remarks_iii_b, String observations_iii_b, String observations_iii_c, String management_iii_c, String remarks_iii_d, String observations_iii_d, String management_iii_d, String observations_iii_e, String management_iii_e, String remarks_iii_e, String observations_iii_f, String management_iii_f, String remarks_iii_f, String observations_iii_g, String management_iii_g, String remarks_iii_g, String observations_iii_h, String management_iii_h, String remarks_iii_h, String observations_iii_i, String management_iii_i, String remarks_iii_i, String observations_iv_a, String management_iv_a, String remarks_iv_a, String observations_iv_b, String management_iv_b, String remarks_iv_b, String observations_iv_c, String management_iv_c, String remarks_iv_c, String observations_iv_d, String management_iv_d, String remarks_iv_d, String observations_v_a, String management_v_a, String remarks_v_a, String observations_v_b, String management_v_b, String remarks_v_b, String observations_v_c, String management_v_c, String remarks_v_c, String observations_v_d, String management_v_d, String remarks_v_d, String observations_vi_a, String management_vi_a, String remarks_vi_a, String observations_vi_b, String management_vi_b, String remarks_vi_b, String observations_vi_c, String management_vi_c, String remarks_vi_c, String observations_vi_d, String management_vi_d, String remarks_vi_d, String observations_vi_e, String management_vi_e, String remarks_vi_e, String observations_vii_a, String management_vii_a, String remarks_vii_a, String observations_vii_b, String management_vii_b, String remarks_vii_b, String observations_vii_c, String management_vii_c, String remarks_vii_c, String observations_vii_d, String management_vii_d, String remarks_vii_d, String observations_vii_e, String management_vii_e, String remarks_vii_e, String observations_vii_f, String management_vii_f, String remarks_vii_f, String observations_vii_g, String management_vii_g, String remarks_vii_g, String observations_viii_a, String management_viii_a, String remarks_viii_a, String management_ix_a, String observations_ix_a, String remarks_ix_a, String observations_ix_b, String management_ix_b, String remarks_ix_b, String observations_ix_c, String management_ix_c, String remarks_ix_c, String observations_ix_d, String management_ix_d, String remarks_ix_d, String observations_x_a, String management_x_a, String remarks_x_a, String observations_x_b, String management_x_b, String remarks_x_b, String observations_x_c, String management_x_c, String remarks_x_c, String observations_x_d, String management_x_d, String remarks_x_d, String observations_x_e, String management_x_e, String remarks_x_e, String observations_x_f, String management_x_f, String remarks_x_f, String observations_x_g, String management_x_g, String remarks_x_g, String observations_xi_a, String management_xi_a, String remarks_xi_a, String observations_xi_b, String management_xi_b, String remarks_xi_b, String observations_xi_c, String management_xi_c, String remarks_xi_c, String observations_xi_d, String management_xi_d, String remarks_xi_d, String observations_xii_a, String management_xii_a, String remarks_xii_a, String observations_xii_b, String management_xii_b, String remarks_xii_b, String observations_xii_c, String management_xii_c, String remarks_xii_c, String observations_xii_d, String management_xii_d, String remarks_xii_d, String observations_xii_e, String management_xii_e, String remarks_xii_e, String observations_xiii_a, String management_xiii_a, String remarks_xiii_a, String observations_xiii_b, String management_xiii_b, String remarks_xiii_b, String observations_xiii_c, String management_xiii_c, String remarks_xiii_c, String observations_xiii_d, String management_xiii_d, String remarks_xiii_d, String observation_xiv__a, String management_xiv_a, String remarks_xiv_a, String observations_xiv_b, String management_xiv_b, String remarks_xiv_b, String observations_xiv_c, String management_xiv_c, String remarks_xiv_c, String observations_xiv_d, String management_xiv_d, String remarks_xiv_d, String observations_xiv_e, String management_xiv_e, String remarks_xiv_e, String observations_xiv_f, String management_xiv_f, String remarks_xiv_f, String observations_xiv_g, String management_xiv_g, String remarks_xiv_g, String observations_xv_a, String management_xv_a, String remarks_xv_a, String observations_xv_b, String management_xv_b, String remarks_xv_b, String observations_xv_c, String management_xv_c, String remarks_xv_c, String observations_xvi_a, String management_xvi_a, String remarks_xvi_a, String observations_xvi_b, String management_xvi_b, String remarks_xvi_b, String observations_xvii_a, String management_xvii_a, String remarks_xvii_a, String observations_xvii_b, String management_xvii_b, String observations_xvii_c, String remarks_xvii_b, String management_xvii_c, String remarks_xvii_c, String observations_xvii_d, String management_xvii_d, String remarks_xvii_d, String observations_xviii_a, String management_xviii_a, String remarks_xviii_a, String observations_xviii_b, String management_xviii_b, String remarks_xviii_b, String observations_xviii_c, String management_xviii_c, String observations_xviii_d, String remarks_xviii_c, String management_xviii_d, String observations_xix_a, String management_xix_a, String remarks_xix_a, String remarks_xviii_d, long createdby, String filenoi, Date datei, String namei, String adressi, String subject, Date dateii, Date dateiii) {
		ObservationOnInternal observationOnInternal = observationOnInternalLocalService.createObservationOnInternal(CounterLocalServiceUtil.increment(ObservationOnInternal.class.getName()));
		
		observationOnInternal.setFilenoi(filenoi);
		observationOnInternal.setDatei(datei);
		observationOnInternal.setNamei(namei);
		observationOnInternal.setAdressi(adressi);
		observationOnInternal.setSubject(subject);
		observationOnInternal.setDateii(dateii);
		observationOnInternal.setDateiii(dateiii);
		observationOnInternal.setObservations_i_a(observations_i_a);
		observationOnInternal.setManagement_i_a(management_i_a);
		observationOnInternal.setRemarks_i_a(remarks_i_a);
		observationOnInternal.setObservations_i_b(observations_i_b);
		observationOnInternal.setManagement_i_b(management_i_b);
		observationOnInternal.setRemarks_i_b(remarks_i_b);
		observationOnInternal.setObservations_i_c(observations_i_c);
		observationOnInternal.setManagement_i_c(management_i_c);
		observationOnInternal.setRemarks_i_c(remarks_i_c);
		observationOnInternal.setObservations_i_d(observations_i_d);
		observationOnInternal.setManagement_i_d(management_i_d);
		observationOnInternal.setRemarks_i_d(remarks_i_d);
		observationOnInternal.setObservations_ii_a(observations_ii_a);
		observationOnInternal.setManagement_ii_a(management_ii_a);
		observationOnInternal.setRemarks_ii_a(remarks_ii_a);
		observationOnInternal.setObservations_ii_b(observations_ii_b);
		observationOnInternal.setManagement_ii_b(management_ii_b);
		observationOnInternal.setRemarks_ii_b(remarks_ii_b);
		observationOnInternal.setObservations_ii_c(observations_ii_c);
		observationOnInternal.setManagement_ii_c(management_ii_c);
		observationOnInternal.setRemarks_ii_c(remarks_ii_c);
		observationOnInternal.setObservations_ii_d(observations_ii_d);
		observationOnInternal.setManagement_ii_d(management_ii_d);
		observationOnInternal.setRemarks_ii_d(remarks_ii_d);
		observationOnInternal.setObservations_iii_a(observations_iii_a);
		observationOnInternal.setManagement_iii_a(management_iii_a);
		observationOnInternal.setRemarks_iii_a(remarks_iii_a);
		observationOnInternal.setObservations_iii_b(observations_iii_b);
		observationOnInternal.setManagement_iii_b(management_iii_b);
		observationOnInternal.setRemarks_iii_b(remarks_iii_b);
		observationOnInternal.setObservations_iii_c(observations_iii_c);
		observationOnInternal.setManagement_iii_c(management_iii_c);
		observationOnInternal.setRemarks_iii_d(remarks_iii_d);
		observationOnInternal.setObservations_iii_d(observations_iii_d);
		observationOnInternal.setManagement_iii_d(management_iii_d);
		observationOnInternal.setRemarks_iii_d(remarks_iii_d);
		observationOnInternal.setObservations_iii_e(observations_iii_e);
		observationOnInternal.setManagement_iii_e(management_iii_e);
		observationOnInternal.setRemarks_iii_e(remarks_iii_e);
		observationOnInternal.setObservations_iii_f(observations_iii_f);
		observationOnInternal.setManagement_iii_f(management_iii_f);
		observationOnInternal.setRemarks_iii_f(remarks_iii_f);
		observationOnInternal.setObservations_iii_g(observations_iii_g);
		observationOnInternal.setManagement_iii_g(management_iii_g);
		observationOnInternal.setRemarks_iii_g(remarks_iii_g);
		observationOnInternal.setObservations_iii_h(observations_iii_h);
		observationOnInternal.setManagement_iii_h(management_iii_h);
		observationOnInternal.setRemarks_iii_h(remarks_iii_h);
		observationOnInternal.setObservations_iii_i(observations_iii_i);
		observationOnInternal.setManagement_iii_i(management_iii_i);
		observationOnInternal.setRemarks_iii_i(remarks_iii_i);
		observationOnInternal.setObservations_iv_a(observations_iv_a);
		observationOnInternal.setManagement_iv_a(management_iv_a);
		observationOnInternal.setRemarks_iv_a(remarks_iv_a);
		observationOnInternal.setObservations_iv_b(observations_iv_b);
		observationOnInternal.setManagement_iv_b(management_iv_b);
		observationOnInternal.setRemarks_iv_b(remarks_iv_b);
		observationOnInternal.setObservations_iv_c(observations_iv_c);
		observationOnInternal.setManagement_iv_c(management_iv_c);
		observationOnInternal.setRemarks_iv_c(remarks_iv_c);
		observationOnInternal.setObservations_iv_d(observations_iv_d);
		observationOnInternal.setManagement_iv_d(management_iv_d);
		observationOnInternal.setRemarks_iv_d(remarks_iv_d);
		observationOnInternal.setObservations_v_a(observations_v_a);
		observationOnInternal.setManagement_v_a(management_v_a);
		observationOnInternal.setRemarks_v_a(remarks_v_a);
		observationOnInternal.setObservations_v_b(observations_v_b);
		observationOnInternal.setManagement_v_b(management_v_b);
		observationOnInternal.setRemarks_v_b(remarks_v_b);
		observationOnInternal.setObservations_v_c(observations_v_c);
		observationOnInternal.setManagement_v_c(management_v_c);
		observationOnInternal.setRemarks_v_c(remarks_v_c);
		observationOnInternal.setObservations_v_d(observations_v_d);
		observationOnInternal.setManagement_v_d(management_v_d);
		observationOnInternal.setRemarks_v_d(remarks_v_d);
		observationOnInternal.setObservations_vi_a(observations_vi_a);
		observationOnInternal.setManagement_vi_a(management_vi_a);
		observationOnInternal.setRemarks_vi_a(remarks_vi_a);
		observationOnInternal.setObservations_vi_b(observations_vi_b);
		observationOnInternal.setManagement_vi_b(management_vi_b);
		observationOnInternal.setRemarks_vi_b(remarks_vi_b);
		observationOnInternal.setObservations_vi_c(observations_vi_c);
		observationOnInternal.setManagement_vi_c(management_vi_c);
		observationOnInternal.setRemarks_vi_c(remarks_vi_c);
		observationOnInternal.setObservations_vi_d(observations_vi_d);
		observationOnInternal.setManagement_vi_d(management_vi_d);
		observationOnInternal.setRemarks_vi_d(remarks_vi_d);
		observationOnInternal.setObservations_vi_e(observations_vi_e);
		observationOnInternal.setManagement_vi_e(management_vi_e);
		observationOnInternal.setRemarks_vi_e(remarks_vi_e);
		observationOnInternal.setObservations_vii_a(observations_vii_a);
		observationOnInternal.setManagement_vii_a(management_vii_a);
		observationOnInternal.setRemarks_vii_a(remarks_vii_a);
		observationOnInternal.setObservations_vii_b(observations_vii_b);
		observationOnInternal.setManagement_vii_b(management_vii_b);
		observationOnInternal.setRemarks_vii_b(remarks_vii_b);
		observationOnInternal.setObservations_vii_c(observations_vii_c);
		observationOnInternal.setManagement_vii_c(management_vii_c);
		observationOnInternal.setRemarks_vii_c(remarks_vii_c);
		observationOnInternal.setObservations_vii_d(observations_vii_d);
		observationOnInternal.setManagement_vii_d(management_vii_d);
		observationOnInternal.setRemarks_vii_d(remarks_vii_d);
		observationOnInternal.setObservations_vii_e(observations_vii_e);
		observationOnInternal.setManagement_vii_e(management_vii_e);
		observationOnInternal.setRemarks_vii_e(remarks_vii_e);
		observationOnInternal.setObservations_vii_f(observations_vii_f);
		observationOnInternal.setManagement_vii_f(management_vii_f);
		observationOnInternal.setRemarks_vii_f(remarks_vii_f);
		observationOnInternal.setObservations_vii_g(observations_vii_g);
		observationOnInternal.setManagement_vii_g(management_vii_g);
		observationOnInternal.setRemarks_vii_g(remarks_vii_g);
		observationOnInternal.setObservations_viii_a(observations_viii_a);
		observationOnInternal.setManagement_viii_a(management_viii_a);
		observationOnInternal.setRemarks_viii_a(remarks_viii_a);
		observationOnInternal.setObservations_ix_a(observations_ix_a);
		observationOnInternal.setManagement_ix_a(management_ix_a);
		observationOnInternal.setRemarks_ix_a(remarks_ix_a);
		observationOnInternal.setObservations_ix_b(observations_ix_b);
		observationOnInternal.setManagement_ix_b(management_ix_b);
		observationOnInternal.setRemarks_ix_b(remarks_ix_b);
		observationOnInternal.setObservations_ix_c(observations_ix_c);
		observationOnInternal.setManagement_ix_c(management_ix_c);
		observationOnInternal.setRemarks_ix_c(remarks_ix_c);
		observationOnInternal.setObservations_ix_d(observations_ix_d);
		observationOnInternal.setManagement_ix_d(management_ix_d);
		observationOnInternal.setRemarks_ix_d(remarks_ix_d);
		observationOnInternal.setObservations_x_a(observations_x_a);
		observationOnInternal.setManagement_x_a(management_x_a);
		observationOnInternal.setRemarks_x_a(remarks_x_a);
		observationOnInternal.setObservations_x_b(observations_x_b);
		observationOnInternal.setManagement_x_b(management_x_b);
		observationOnInternal.setRemarks_x_b(remarks_x_b);
		observationOnInternal.setObservations_x_c(observations_x_c);
		observationOnInternal.setManagement_x_c(management_x_c);
		observationOnInternal.setRemarks_x_c(remarks_x_c);
		observationOnInternal.setObservations_x_d(observations_x_d);
		observationOnInternal.setManagement_x_d(management_x_d);
		observationOnInternal.setRemarks_x_d(remarks_x_d);
		observationOnInternal.setObservations_x_e(observations_x_e);
		observationOnInternal.setManagement_x_e(management_x_e);
		observationOnInternal.setRemarks_x_e(remarks_x_e);
		observationOnInternal.setObservations_x_f(observations_x_f);
		observationOnInternal.setManagement_x_f(management_x_f);
		observationOnInternal.setRemarks_x_f(remarks_x_f);
		observationOnInternal.setObservations_x_g(observations_x_g);
		observationOnInternal.setManagement_x_g(management_x_g);
		observationOnInternal.setRemarks_x_g(remarks_x_g);
		observationOnInternal.setObservations_xi_a(observations_xi_a);
		observationOnInternal.setManagement_xi_a(management_xi_a);
		observationOnInternal.setRemarks_xi_a(remarks_xi_a);
		observationOnInternal.setObservations_xi_b(observations_xi_b);
		observationOnInternal.setManagement_xi_b(management_xi_b);
		observationOnInternal.setRemarks_xi_b(remarks_xi_b);
		observationOnInternal.setObservations_xi_c(observations_xi_c);
		observationOnInternal.setManagement_xi_c(management_xi_c);
		observationOnInternal.setRemarks_xi_c(remarks_xi_c);
		observationOnInternal.setObservations_xi_d(observations_xi_d);
		observationOnInternal.setManagement_xi_d(management_xi_d);
		observationOnInternal.setRemarks_xi_d(remarks_xi_d);
		observationOnInternal.setObservations_xii_a(observations_xii_a);
		observationOnInternal.setManagement_xii_a(management_xii_a);
		observationOnInternal.setRemarks_xii_a(remarks_xii_a);
		observationOnInternal.setObservations_xii_b(observations_xii_b);
		observationOnInternal.setManagement_xii_b(management_xii_b);
		observationOnInternal.setRemarks_xii_b(remarks_xii_b);
		observationOnInternal.setObservations_xii_c(observations_xii_c);
		observationOnInternal.setManagement_xii_c(management_xii_c);
		observationOnInternal.setRemarks_xii_c(remarks_xii_c);
		observationOnInternal.setObservations_xii_d(observations_xii_d);
		observationOnInternal.setManagement_xii_d(management_xii_d);
		observationOnInternal.setRemarks_xii_d(remarks_xii_d);
		observationOnInternal.setObservations_xii_e(observations_xii_e);
		observationOnInternal.setManagement_xii_e(management_xii_e);
		observationOnInternal.setRemarks_xii_e(remarks_xii_e);
		observationOnInternal.setObservations_xiii_a(observations_xiii_a);
		observationOnInternal.setManagement_xiii_a(management_xiii_a);
		observationOnInternal.setRemarks_xiii_a(remarks_xiii_a);
		observationOnInternal.setObservations_xiii_b(observations_xiii_b);
		observationOnInternal.setManagement_xiii_b(management_xiii_b);
		observationOnInternal.setRemarks_xiii_b(remarks_xiii_b);
		observationOnInternal.setObservations_xiii_c(observations_xiii_c);
		observationOnInternal.setManagement_xiii_c(management_xiii_c);
		observationOnInternal.setRemarks_xiii_c(remarks_xiii_c);
		observationOnInternal.setObservations_xiii_d(observations_xiii_d);
		observationOnInternal.setManagement_xiii_d(management_xiii_d);
		observationOnInternal.setRemarks_xiii_d(remarks_xiii_d);
		observationOnInternal.setObservation_xiv__a(observation_xiv__a);
		observationOnInternal.setManagement_xiv_a(management_xiv_a);
		observationOnInternal.setRemarks_xiv_a(remarks_xiv_a);
		observationOnInternal.setObservations_xiv_b(observations_xiv_b);
		observationOnInternal.setManagement_xiv_b(management_xiv_b);
		observationOnInternal.setRemarks_xiv_b(remarks_xiv_b);
		observationOnInternal.setObservations_xiv_c(observations_xiv_c);
		observationOnInternal.setManagement_xiv_c(management_xiv_c);
		observationOnInternal.setRemarks_xiv_c(remarks_xiv_c);
		observationOnInternal.setObservations_xiv_d(observations_xiv_d);
		observationOnInternal.setManagement_xiv_d(management_xiv_d);
		observationOnInternal.setRemarks_xiv_d(remarks_xiv_d);
		observationOnInternal.setObservations_xiv_e(observations_xiv_e);
		observationOnInternal.setManagement_xiv_e(management_xiv_e);
		observationOnInternal.setRemarks_xiv_e(remarks_xiv_e);
		observationOnInternal.setObservations_xiv_f(observations_xiv_f);
		observationOnInternal.setManagement_xiv_f(management_xiv_f);
		observationOnInternal.setRemarks_xiv_f(remarks_xiv_f);
		observationOnInternal.setObservations_xiv_g(observations_xiv_g);
		observationOnInternal.setManagement_xiv_g(management_xiv_g);
		observationOnInternal.setRemarks_xiv_g(remarks_xiv_g);
		observationOnInternal.setObservations_xv_a(observations_xv_a);
		observationOnInternal.setManagement_xv_a(management_xv_a);
		observationOnInternal.setRemarks_xv_a(remarks_xv_a);
		observationOnInternal.setObservations_xv_b(observations_xv_b);
		observationOnInternal.setManagement_xv_b(management_xv_b);
		observationOnInternal.setRemarks_xv_b(remarks_xv_b);
		observationOnInternal.setObservations_xv_c(observations_xv_c);
		observationOnInternal.setManagement_xv_c(management_xv_c);
		observationOnInternal.setRemarks_xv_c(remarks_xv_c);
		observationOnInternal.setObservations_xvi_a(observations_xvi_a);
		observationOnInternal.setManagement_xvi_a(management_xvi_a);
		observationOnInternal.setRemarks_xvi_a(remarks_xvi_a);
		observationOnInternal.setObservations_xvi_b(observations_xvi_b);
		observationOnInternal.setManagement_xvi_b(management_xvi_b);
		observationOnInternal.setRemarks_xvi_b(remarks_xvi_b);
		observationOnInternal.setObservations_xvii_a(observations_xvii_a);
		observationOnInternal.setManagement_xvii_a(management_xvii_a);
		observationOnInternal.setRemarks_xvii_a(remarks_xvii_a);
		observationOnInternal.setObservations_xvii_b(observations_xvii_b);
		observationOnInternal.setManagement_xvii_b(management_xvii_b);
		observationOnInternal.setRemarks_xvii_b(remarks_xvii_b);
		observationOnInternal.setObservations_xvii_c(observations_xvii_c);
		observationOnInternal.setManagement_xvii_c(management_xvii_c);
		observationOnInternal.setRemarks_xvii_c(remarks_xvii_c);
		observationOnInternal.setObservations_xvii_d(observations_xvii_d);
		observationOnInternal.setManagement_xvii_d(management_xvii_d);
		observationOnInternal.setRemarks_xvii_d(remarks_xvii_d);
		observationOnInternal.setObservations_xviii_a(observations_xviii_a);
		observationOnInternal.setManagement_xviii_a(management_xviii_a);
		observationOnInternal.setRemarks_xviii_a(remarks_xviii_a);
		observationOnInternal.setObservations_xviii_b(observations_xviii_b);
		observationOnInternal.setManagement_xviii_b(management_xviii_b);
		observationOnInternal.setRemarks_xviii_b(remarks_xviii_b);
		observationOnInternal.setObservations_xviii_c(observations_xviii_c);
		observationOnInternal.setManagement_xviii_c(management_xviii_c);
		observationOnInternal.setRemarks_xviii_c(remarks_xviii_c);
		observationOnInternal.setObservations_xviii_d(observations_xviii_d);
		observationOnInternal.setManagement_xviii_d(management_xviii_d);
		observationOnInternal.setRemarks_xviii_d(remarks_xviii_d);
		observationOnInternal.setObservations_xix_a(observations_xix_a);
		observationOnInternal.setManagement_xix_a(management_xix_a);
		observationOnInternal.setRemarks_xix_a(remarks_xix_a);
		observationOnInternal.setCreatedby(createdby);
		Date createdate=new Date();
		observationOnInternal.setCreatedate(createdate);
		
		
		return ObservationOnInternalLocalServiceUtil.addObservationOnInternal(observationOnInternal);
		
	}*/
}