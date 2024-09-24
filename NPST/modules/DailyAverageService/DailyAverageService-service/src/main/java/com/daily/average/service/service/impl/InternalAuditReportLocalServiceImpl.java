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

import com.daily.average.service.model.InternalAuditReport;
import com.daily.average.service.service.base.InternalAuditReportLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class InternalAuditReportLocalServiceImpl
	extends InternalAuditReportLocalServiceBaseImpl {
	/**
	 * 
	 * @param observations_i_a
	 * @param management_i_a
	 * @param observations_i_b
	 * @param management_i_b
	 * @param observations_i_c
	 * @param management_i_c
	 * @param observations_i_d
	 * @param observations_ii_a
	 * @param management_ii_a
	 * @param observations_ii_b
	 * @param management_ii_b
	 * @param observations_ii_c
	 * @param management_ii_c
	 * @param observations_ii_d
	 * @param management_ii_d
	 * @param observations_iii_a
	 * @param management_iii_a
	 * @param observations_iii_b
	 * @param management_iii_b
	 * @param observations_iii_c
	 * @param management_iii_c
	 * @param observations_iii_d
	 * @param management_iii_d
	 * @param observations_iii_e
	 * @param management_iii_e
	 * @param observations_iii_f
	 * @param management_iii_f
	 * @param observations_iii_g
	 * @param management_iii_g
	 * @param observations_iii_h
	 * @param management_iii_h
	 * @param observations_iii_i
	 * @param management_iii_i
	 * @param management_iv_a
	 * @param observations_iv_b
	 * @param observations_iv_a
	 * @param management_iv_b
	 * @param observations_iv_c
	 * @param observations_iv_d
	 * @param management_iv_d
	 * @param management_iv_c
	 * @param observations_v_a
	 * @param management_v_a
	 * @param observations_v_b
	 * @param observations_v_c
	 * @param management_v_b
	 * @param management_v_c
	 * @param observations_v_d
	 * @param management_v_d
	 * @param observations_vi_a
	 * @param management_vi_a
	 * @param observations_vi_b
	 * @param management_vi_b
	 * @param observations_vi_c
	 * @param management_vi_c
	 * @param observations_vi_d
	 * @param management_vi_d
	 * @param observations_vi_e
	 * @param management_vi_e
	 * @param observations_vii_a
	 * @param management_vii_a
	 * @param observations_vii_b
	 * @param management_vii_b
	 * @param observations_vii_c
	 * @param management_vii_c
	 * @param observations_vii_d
	 * @param management_vii_d
	 * @param observations_vii_e
	 * @param management_vii_e
	 * @param observations_vii_f
	 * @param management_vii_f
	 * @param observations_vii_g
	 * @param management_vii_g
	 * @param management_viii_a
	 * @param observations_ix_a
	 * @param management_ix_a
	 * @param observations_viii_a
	 * @param observations_ix_b
	 * @param management_ix_b
	 * @param observations_ix_c
	 * @param management_ix_c
	 * @param observations_ix_d
	 * @param management_ix_d
	 * @param observations_x_a
	 * @param management_x_a
	 * @param observations_x_b
	 * @param management_x_b
	 * @param observations_x_c
	 * @param management_x_c
	 * @param observations_x_d
	 * @param management_x_d
	 * @param observations_x_e
	 * @param management_x_e
	 * @param observations_x_f
	 * @param management_x_f
	 * @param observations_x_g
	 * @param management_x_g
	 * @param observations_xi_a
	 * @param management_xi_a
	 * @param observations_xi_b
	 * @param management_xi_b
	 * @param observations_xi_c
	 * @param management_xi_c
	 * @param observations_xi_d
	 * @param management_xi_d
	 * @param observations_xii_a
	 * @param management_xii_a
	 * @param observations_xii_b
	 * @param management_xii_b
	 * @param observations_xii_c
	 * @param management_xii_c
	 * @param observations_xii_d
	 * @param management_xii_d
	 * @param observations_xii_e
	 * @param management_xii_e
	 * @param observations_xiii_a
	 * @param management_xiii_a
	 * @param observations_xiii_b
	 * @param management_xiii_b
	 * @param observations_xiii_c
	 * @param management_xiii_c
	 * @param observations_xiii_d
	 * @param management_xiii_d
	 * @param observation_xiv__a
	 * @param management_xiv_a
	 * @param observations_xiv_b
	 * @param management_xiv_b
	 * @param observations_xiv_c
	 * @param management_xiv_c
	 * @param observations_xiv_d
	 * @param management_xiv_d
	 * @param observations_xiv_e
	 * @param management_xiv_e
	 * @param observations_xiv_f
	 * @param management_xiv_f
	 * @param observations_xiv_g
	 * @param management_xiv_g
	 * @param observations_xv_a
	 * @param management_xv_a
	 * @param observations_xv_b
	 * @param management_xv_b
	 * @param management_xv_c
	 * @param observations_xv_c
	 * @param observations_xvi_a
	 * @param management_xvi_a
	 * @param observations_xvi_b
	 * @param management_xvi_b
	 * @param observations_xvii_a
	 * @param management_xvii_a
	 * @param observations_xvii_b
	 * @param management_xvii_b
	 * @param observations_xvii_c
	 * @param management_xvii_c
	 * @param observations_xvii_d
	 * @param management_xvii_d
	 * @param observations_xviii_a
	 * @param management_xviii_a
	 * @param observations_xviii_b
	 * @param management_xviii_b
	 * @param observations_xviii_c
	 * @param management_xviii_c
	 * @param observations_xviii_d
	 * @param management_xviii_d
	 * @param observations_xix_a
	 * @param management_xix_a
	 * @param createdby 
	 * @param management_i_d 
	 * @return
	 */
	 
	public InternalAuditReport addInternalAuditReport(String observations_i_a, String management_i_a, String observations_i_b, String management_i_b, String observations_i_c, String management_i_c, String observations_i_d, String observations_ii_a, String management_ii_a, String observations_ii_b, String management_ii_b, String observations_ii_c, String management_ii_c, String observations_ii_d, String management_ii_d, String observations_iii_a, String management_iii_a, String observations_iii_b, String management_iii_b, String observations_iii_c, 
														String management_iii_c, String observations_iii_d, String management_iii_d, String observations_iii_e, String management_iii_e, String observations_iii_f, String management_iii_f, String observations_iii_g, String management_iii_g, String observations_iii_h, String management_iii_h, String observations_iii_i, String management_iii_i, String management_iv_a, String observations_iv_b, String observations_iv_a, String management_iv_b, String observations_iv_c, String observations_iv_d, String management_iv_d, 
														String management_iv_c, String observations_v_a, String management_v_a, String observations_v_b, String observations_v_c, String management_v_b, String management_v_c, String observations_v_d, String management_v_d, String observations_vi_a, String management_vi_a, String observations_vi_b, String management_vi_b, String observations_vi_c, String management_vi_c, String observations_vi_d, String management_vi_d, String observations_vi_e, String management_vi_e, String observations_vii_a, String management_vii_a, 
														String observations_vii_b, String management_vii_b, String observations_vii_c, String management_vii_c, String observations_vii_d, String management_vii_d, String observations_vii_e, String management_vii_e, String observations_vii_f, String management_vii_f, String observations_vii_g, String management_vii_g, String management_viii_a, String observations_ix_a, String management_ix_a, String observations_viii_a, String observations_ix_b, String management_ix_b, String observations_ix_c, String management_ix_c, 
														String observations_ix_d, String management_ix_d, String observations_x_a, String management_x_a, String observations_x_b, String management_x_b, String observations_x_c, String management_x_c, String observations_x_d, String management_x_d, String observations_x_e, String management_x_e, String observations_x_f, String management_x_f, String observations_x_g, String management_x_g, String observations_xi_a, String management_xi_a, String observations_xi_b, String management_xi_b, String observations_xi_c, 
														String management_xi_c, String observations_xi_d, String management_xi_d, String observations_xii_a, String management_xii_a, String observations_xii_b, String management_xii_b, String observations_xii_c, String management_xii_c, String observations_xii_d, String management_xii_d, String observations_xii_e, String management_xii_e, String observations_xiii_a, String management_xiii_a, String observations_xiii_b, String management_xiii_b, String observations_xiii_c, String management_xiii_c, String observations_xiii_d, String management_xiii_d, String observation_xiv__a, String management_xiv_a, String observations_xiv_b, String management_xiv_b, String observations_xiv_c, String management_xiv_c, 
														String observations_xiv_d, String management_xiv_d, String observations_xiv_e, String management_xiv_e, String observations_xiv_f, String management_xiv_f, String observations_xiv_g, String management_xiv_g, String observations_xv_a, String management_xv_a, String observations_xv_b, String management_xv_b, String management_xv_c, String observations_xv_c, String observations_xvi_a, String management_xvi_a, String observations_xvi_b, String management_xvi_b, String observations_xvii_a, String management_xvii_a, String observations_xvii_b, String management_xvii_b, String observations_xvii_c, String management_xvii_c, String observations_xvii_d, String management_xvii_d, String observations_xviii_a, 
														String management_xviii_a, String observations_xviii_b, String management_xviii_b, String observations_xviii_c, String management_xviii_c, String observations_xviii_d, String management_xviii_d, String observations_xix_a, String management_xix_a, long createdby, String management_i_d) {
		InternalAuditReport internalAuditReport = internalAuditReportLocalService.createInternalAuditReport(CounterLocalServiceUtil.increment(InternalAuditReport.class.getName()));
		internalAuditReport.setObservations_i_a(observations_i_a);
		internalAuditReport.setManagement_i_a(management_i_a);
		internalAuditReport.setObservations_i_b(observations_i_b);
		internalAuditReport.setManagement_i_b(management_i_b);
		internalAuditReport.setObservations_i_c(observations_i_c);
		internalAuditReport.setManagement_i_c(management_i_c);
		internalAuditReport.setObservations_i_d(observations_i_d);
		internalAuditReport.setManagement_i_d(management_i_d);
		internalAuditReport.setObservations_ii_a(observations_ii_a);
		internalAuditReport.setManagement_ii_a(management_ii_a);
		internalAuditReport.setObservations_ii_b(observations_ii_b);
		internalAuditReport.setManagement_ii_b(management_ii_b);
		internalAuditReport.setObservations_ii_c(observations_ii_c);
		internalAuditReport.setManagement_ii_c(management_ii_c);
		internalAuditReport.setObservations_ii_d(observations_ii_d);
		internalAuditReport.setManagement_ii_d(management_ii_d);
		internalAuditReport.setObservations_iii_a(observations_iii_a);
		internalAuditReport.setManagement_iii_a(management_iii_a);
		internalAuditReport.setObservations_iii_b(observations_iii_b);
		internalAuditReport.setManagement_iii_b(management_iii_b);
		internalAuditReport.setObservations_iii_c(observations_iii_c);
		internalAuditReport.setManagement_iii_c(management_iii_c);
		internalAuditReport.setObservations_iii_d(observations_iii_d);
		internalAuditReport.setManagement_iii_d(management_iii_d);
		internalAuditReport.setObservations_iii_e(observations_iii_e);
		internalAuditReport.setManagement_iii_e(management_iii_e);
		internalAuditReport.setObservations_iii_f(observations_iii_f);
		internalAuditReport.setManagement_iii_f(management_iii_f);
		internalAuditReport.setObservations_iii_g(observations_iii_g);
		internalAuditReport.setManagement_iii_g(management_iii_g);
		internalAuditReport.setObservations_iii_h(observations_iii_h);
		internalAuditReport.setManagement_iii_h(management_iii_h);
		internalAuditReport.setObservations_iii_i(observations_iii_i);
		internalAuditReport.setManagement_iii_i(management_iii_i);
		internalAuditReport.setObservations_iv_a(observations_iv_a);
		internalAuditReport.setManagement_iv_a(management_iv_a);
		internalAuditReport.setObservations_iv_b(observations_iv_b);
		internalAuditReport.setManagement_iv_b(management_iv_b);
		internalAuditReport.setObservations_iv_c(observations_iv_c);
		internalAuditReport.setManagement_iv_c(management_iv_c);
		internalAuditReport.setObservations_iv_d(observations_iv_d);
		internalAuditReport.setManagement_iv_d(management_iv_d);
		internalAuditReport.setObservations_v_a(observations_v_a);
		internalAuditReport.setManagement_v_a(management_v_a);
		internalAuditReport.setObservations_v_b(observations_v_b);
		internalAuditReport.setManagement_v_b(management_v_b);
		internalAuditReport.setObservations_v_c(observations_v_c);
		internalAuditReport.setManagement_v_c(management_v_c);
		internalAuditReport.setObservations_v_d(observations_v_d);
		internalAuditReport.setManagement_v_d(management_v_d);
		internalAuditReport.setObservations_vi_a(observations_vi_a);
		internalAuditReport.setManagement_vi_a(management_vi_a);
		internalAuditReport.setObservations_vi_b(observations_vi_b);
		internalAuditReport.setManagement_vi_b(management_vi_b);
		internalAuditReport.setObservations_vi_c(observations_vi_c);
		internalAuditReport.setManagement_vi_c(management_vi_c);
		internalAuditReport.setObservations_vi_d(observations_vi_d);
		internalAuditReport.setManagement_vi_d(management_vi_d);
		internalAuditReport.setObservations_vi_e(observations_vi_e);
		internalAuditReport.setManagement_vi_e(management_vi_e);
		internalAuditReport.setObservations_vii_a(observations_vii_a);
		internalAuditReport.setManagement_vii_a(management_vii_a);
		internalAuditReport.setObservations_vii_b(observations_vii_b);
		internalAuditReport.setManagement_vii_b(management_vii_b);
		internalAuditReport.setObservations_vii_c(observations_vii_c);
		internalAuditReport.setManagement_vii_c(management_vii_c);
		internalAuditReport.setObservations_vii_d(observations_vii_d);
		internalAuditReport.setManagement_vii_d(management_vii_d);
		internalAuditReport.setObservations_vii_e(observations_vii_e);
		internalAuditReport.setManagement_vii_e(management_vii_e);
		internalAuditReport.setObservations_vii_f(observations_vii_f);
		internalAuditReport.setManagement_vii_f(management_vii_f);
		internalAuditReport.setObservations_vii_g(observations_vii_g);
		internalAuditReport.setManagement_vii_g(management_vii_g);
		internalAuditReport.setObservations_viii_a(observations_viii_a);
		internalAuditReport.setManagement_viii_a(management_viii_a);
		internalAuditReport.setObservations_ix_a(observations_ix_a);
		internalAuditReport.setManagement_ix_a(management_ix_a);
		internalAuditReport.setObservations_ix_b(observations_ix_b);
		internalAuditReport.setManagement_ix_b(management_ix_b);
		internalAuditReport.setObservations_ix_c(observations_ix_c);
		internalAuditReport.setManagement_ix_c(management_ix_c);
		internalAuditReport.setObservations_ix_d(observations_ix_d);
		internalAuditReport.setManagement_ix_d(management_ix_d);
		internalAuditReport.setObservations_x_a(observations_x_a);
		internalAuditReport.setManagement_x_a(management_x_a);
		internalAuditReport.setObservations_x_b(observations_x_b);
		internalAuditReport.setManagement_x_b(management_x_b);
		internalAuditReport.setObservations_x_c(observations_x_c);
		internalAuditReport.setManagement_x_c(management_x_c);
		internalAuditReport.setObservations_x_d(observations_x_d);
		internalAuditReport.setManagement_x_d(management_x_d);
		internalAuditReport.setObservations_x_e(observations_x_e);
		internalAuditReport.setManagement_x_e(management_x_e);
		internalAuditReport.setObservations_x_f(observations_x_f);
		internalAuditReport.setManagement_x_f(management_x_f);
		internalAuditReport.setObservations_x_g(observations_x_g);
		internalAuditReport.setManagement_x_g(management_x_g);
		internalAuditReport.setObservations_xi_a(observations_xi_a);
		internalAuditReport.setManagement_xi_a(management_xi_a);
		internalAuditReport.setObservations_xi_b(observations_xi_b);
		internalAuditReport.setManagement_xi_b(management_xi_b);
		internalAuditReport.setObservations_xi_c(observations_xi_c);
		internalAuditReport.setManagement_xi_c(management_xi_c);
		internalAuditReport.setObservations_xi_d(observations_xi_d);
		internalAuditReport.setManagement_xi_d(management_xi_d);
		internalAuditReport.setObservations_xii_a(observations_xii_a);
		internalAuditReport.setManagement_xii_a(management_xii_a);
		internalAuditReport.setObservations_xii_b(observations_xii_b);
		internalAuditReport.setManagement_xii_b(management_xii_b);
		internalAuditReport.setObservations_xii_c(observations_xii_c);
		internalAuditReport.setManagement_xii_c(management_xii_c);
		internalAuditReport.setObservations_xii_d(observations_xii_d);
		internalAuditReport.setManagement_xii_d(management_xii_d);
		internalAuditReport.setObservations_xii_e(observations_xii_e);
		internalAuditReport.setManagement_xii_e(management_xii_e);
		internalAuditReport.setObservations_xiii_a(observations_xiii_a);
		internalAuditReport.setManagement_xiii_a(management_xiii_a);
		internalAuditReport.setObservations_xiii_b(observations_xiii_b);
		internalAuditReport.setManagement_xiii_b(management_xiii_b);
		internalAuditReport.setObservations_xiii_c(observations_xiii_c);
		internalAuditReport.setManagement_xiii_c(management_xiii_c);
		internalAuditReport.setObservations_xiii_d(observations_xiii_d);
		internalAuditReport.setManagement_xiii_d(management_xiii_d);
		internalAuditReport.setObservation_xiv__a(observation_xiv__a);
		internalAuditReport.setManagement_xiv_a(management_xiv_a);
		internalAuditReport.setObservations_xiv_b(observations_xiv_b);
		internalAuditReport.setManagement_xiv_b(management_xiv_b);
		internalAuditReport.setObservations_xiv_c(observations_xiv_c);
		internalAuditReport.setManagement_xiv_c(management_xiv_c);
		internalAuditReport.setObservations_xiv_d(observations_xiv_d);
		internalAuditReport.setManagement_xiv_d(management_xiv_d);
		internalAuditReport.setObservations_xiv_e(observations_xiv_e);
		internalAuditReport.setManagement_xiv_e(management_xiv_e);
		internalAuditReport.setObservations_xiv_f(observations_xiv_f);
		internalAuditReport.setManagement_xiv_f(management_xiv_f);
		internalAuditReport.setObservations_xiv_g(observations_xiv_g);
		internalAuditReport.setManagement_xiv_g(management_xiv_g);
		internalAuditReport.setObservations_xv_a(observations_xv_a);
		internalAuditReport.setManagement_xv_a(management_xv_a);
		internalAuditReport.setObservations_xv_b(observations_xv_b);
		internalAuditReport.setManagement_xv_b(management_xv_b);
		internalAuditReport.setObservations_xv_c(observations_xv_c);
		internalAuditReport.setManagement_xv_c(management_xv_c);
		internalAuditReport.setObservations_xvi_a(observations_xvi_a);
		internalAuditReport.setManagement_xvi_a(management_xvi_a);
		internalAuditReport.setObservations_xvi_b(observations_xvi_b);
		internalAuditReport.setManagement_xvi_b(management_xvi_b);
		internalAuditReport.setObservations_xvii_a(observations_xvii_a);
		internalAuditReport.setManagement_xvii_a(management_xvii_a);
		internalAuditReport.setObservations_xvii_b(observations_xvii_b);
		internalAuditReport.setManagement_xvii_b(management_xvii_b);
		internalAuditReport.setObservations_xvii_c(observations_xvii_c);
		internalAuditReport.setManagement_xvii_c(management_xvii_c);
		internalAuditReport.setObservations_xvii_d(observations_xvii_d);
		internalAuditReport.setManagement_xvii_d(management_xvii_d);
		internalAuditReport.setObservations_xviii_a(observations_xviii_a);
		internalAuditReport.setManagement_xviii_a(management_xviii_a);
		internalAuditReport.setObservations_xviii_b(observations_xviii_b);
		internalAuditReport.setManagement_xviii_b(management_xviii_b);
		internalAuditReport.setObservations_xviii_c(observations_xviii_c);
		internalAuditReport.setManagement_xviii_c(management_xviii_c);
		internalAuditReport.setObservations_xviii_d(observations_xviii_d);
		internalAuditReport.setManagement_xviii_d(management_xviii_d);
		internalAuditReport.setObservations_xix_a(observations_xix_a);
		internalAuditReport.setManagement_xix_a(management_xix_a);
		internalAuditReport.setCreatedby(createdby);
		Date createdate=new Date();
		internalAuditReport.setCreatedate(createdate);
		
		return internalAuditReportLocalService.addInternalAuditReport(internalAuditReport);
	}
}