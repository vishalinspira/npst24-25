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

import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.Pfm_Qr_Internal_Audit_Report;
import com.daily.average.service.model.ReportUploadLog;
import com.daily.average.service.model.impl.Pfm_Qr_Internal_Audit_ReportImpl;
import com.daily.average.service.service.base.Pfm_Qr_Internal_Audit_ReportLocalServiceBaseImpl;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalServiceUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class Pfm_Qr_Internal_Audit_ReportLocalServiceImpl
	extends Pfm_Qr_Internal_Audit_ReportLocalServiceBaseImpl {
	
	

	

	/*public Pfm_Qr_Internal_Audit_Report addPfm_Qr_Internal_Audit_Report(long reportUploadLogId, String observations_i_a, String management_i_a, 
						String observations_i_b, String management_i_b, String observations_i_c, String management_i_c, String observations_i_e,
						String management_i_e, String observations_i_d, String management_i_d, String observations_ii_a, String management_ii_a,
						String observations_ii_b, String management_ii_b, String observations_ii_c, String management_ii_c, String observations_ii_d,
						String management_ii_d, String observations_ii_e, String management_ii_e, String observations_ii_f, String management_ii_f,
						String observations_ii_g, String management_ii_g, String observations_iii_a, String management_iii_a,
						String observations_iii_b, String management_iii_b, String observations_iii_c, String management_iii_c,
						String observations_iii_d, String management_iii_d, String observations_iii_e, String management_iii_e,
						String observations_iv_a, String management_iv_a, String observations_iv_b, String management_iv_b,
						String observations_iv_c, String management_iv_c, String observations_iv_d, String management_iv_d,
						String observations_iv_e, String management_iv_e, String observations_iv_f, String management_iv_f,
						String observations_iv_g, String management_iv_g, String observations_iv_h, String management_iv_h,
						String observations_v_a, String management_v_a, String observations_v_b, String management_v_b,
						String observations_v_c, String management_v_c, String observations_v_d, String management_v_d,
						String observations_vi_a, String management_vi_a, String observations_vi_b, String management_vi_b,
						String observations_vi_c, String management_vi_c, String observations_vi_d, String management_vi_d,
						String observations_vi_e, String management_vi_e, String observations_vi_f, String management_vi_f,
						String observations_vi_g, String management_vi_g, String observations_vi_h, String management_vi_h,
						String observations_vi_i, String management_vi_i, String observations_vi_j, String management_vi_j,
						String observations_vi_k, String management_vi_k, String observations_vi_l, String management_vi_l, 
						String observations_vi_m, String management_vi_m, String observations_vii_a, String management_vii_a,
						String observations_vii_b, String management_vii_b, String observations_vii_c, String management_vii_c,
						String observations_vii_d, String management_vii_d, String observations_vii_e, String management_vii_e,
						String observations_vii_f, String management_vii_f,String observations_vii_g, String management_vii_g, String observations_vii_h,
						String management_vii_h,String observations_vii_i, String management_vii_i,String observations_viii_a, String management_viii_a,
						String observations_viii_b, String management_viii_b, String observations_viii_c, String management_viii_c,
						String observations_viii_d, String management_viii_d, String observations_ix_a, String management_ix_a,
						String observations_ix_b, String management_ix_b, String observations_ix_c, String management_ix_c,
						String observations_ix_d, String management_ix_d, String observations_x_a, String management_x_a,
						String observations_x_b, String management_x_b, String observations_x_c, String management_x_c,
						String observations_x_d, String management_x_d, String observations_x_e, String management_x_e,
						String observations_x_f, String management_x_f, String observations_xi_a, String management_xi_a,
						String observations_xi_b, String management_xi_b, String observations_xii_a, String management_xii_a,
						String observations_xii_b, String management_xii_b, String observations_xiii_a, String management_xiii_a,
						String observations_xiii_b, String management_xiii_b, String observations_xiii_c, String management_xiii_c, 
						String observations_xiv_a, String management_xiv_a, String observations_xiv_b, String management_xiv_b,
						String observations_xiv_c, String management_xiv_c, String observations_xv_a, String management_xv_a,
						String observations_xv_b, String management_xv_b, String observations_xvi_a, String management_xvi_a,
						String observations_xvi_b, String management_xvi_b, String observations_xvi_c, String management_xvi_c,
						String observations_xvii_a, String management_xvii_a, String observations_xvii_b, String management_xvii_b,
						String observations_xviii_a, String management_xviii_a, String observations_xviii_b, String management_xviii_b,
						String observations_xviii_c, String management_xviii_c, String observations_xviii_d, String management_xviii_d,
						String observations_xviii_e, String management_xviii_e, String observations_xviii_f, String management_xviii_f,
						String observations_xviii_g, String management_xviii_g, String observations_xviii_h, String management_xviii_h,
						String observations_xviii_i, String management_xviii_i, String observations_xviii_j, String management_xviii_j,
						String observations_xviii_k, String management_xviii_k, String observations_xix_a, String management_xix_a,
						String observations_xix_b, String management_xix_b, String observations_xix_c, String management_xix_c, 
						String observations_xix_d, String management_xix_d, String observations_xix_e, String management_xix_e,
						String observations_xix_f, String management_xix_f, String observations_xx_a, String management_xx_a,
						String observations_xx_b, String management_xx_b, String observations_xx_c, String management_xx_c,
						String observations_xx_d, String management_xx_d, String observations_xx_e, String management_xx_e,
						String observations_xxi_a, String management_xxi_a, String observations_xxi_b, String management_xxi_b,
						String observations_xxi_c, String management_xxi_c, String observations_xxi_d, String management_xxi_d,
						String observations_xxi_e, String management_xxi_e, String observations_xxii_a, String management_xxii_a,
						String observations_xxii_b, String management_xxii_b, String observations_xxii_c, String management_xxii_c,
						String observations_xxii_d, String management_xxii_d, String observations_xxiii_a, String management_xxiii_a,
						String observations_xxiii_b, String management_xxiii_b, String observations_xxiii_c, String management_xxiii_c,
						String observations_xxiii_d, String management_xxiii_d, String observations_xxiii_e, String management_xxiii_e,
						String observations_xxiv_a, String management_xxiv_a, String observations_xxv_a, String management_xxv_a,
						Date createdon, long createdby, long reportMasterId, long annex_comp_certificate, long extracts_board_pdf,
						long annex_comp_certificate_i, long annex_comp_certificate_ii, long annex_comp_certificate_iii)*/
	
	public Pfm_Qr_Internal_Audit_Report addPfm_Qr_Internal_Audit_Report(Long reportUploadLogId, 
				Date createdon, long createdby, long reportMasterId, long annex_comp_certificate, long extracts_board_pdf, 
				long annex_comp_certificate_i, long annex_comp_certificate_ii, long annex_comp_certificate_iii, String maker_comment_details){
					
		
		
		Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.fetchByPrimaryKey(reportUploadLogId);
		if (pfm_Qr_Internal_Audit_Report == null) {
			pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.create(reportUploadLogId);
		}
	
		
		/*pfm_Qr_Internal_Audit_Report.setObservations_i_a(observations_i_a);
		pfm_Qr_Internal_Audit_Report.setManagement_i_a(management_i_a);
		pfm_Qr_Internal_Audit_Report.setObservations_i_b(observations_i_b);
		pfm_Qr_Internal_Audit_Report.setManagement_i_b(management_i_b);	
		pfm_Qr_Internal_Audit_Report.setObservations_i_c(observations_i_c);
		pfm_Qr_Internal_Audit_Report.setManagement_i_c(management_i_c);
		pfm_Qr_Internal_Audit_Report.setObservations_i_e(observations_i_e);
		pfm_Qr_Internal_Audit_Report.setManagement_i_e(management_i_e);
		pfm_Qr_Internal_Audit_Report.setObservations_i_d(observations_i_d);
		pfm_Qr_Internal_Audit_Report.setManagement_i_d(management_i_d);
		pfm_Qr_Internal_Audit_Report.setObservations_ii_a(observations_ii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_a(management_ii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_ii_b(observations_ii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_b(management_ii_b);	
		pfm_Qr_Internal_Audit_Report.setObservations_ii_c(observations_ii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_c(management_ii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_ii_d(observations_ii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_d(management_ii_d);
		pfm_Qr_Internal_Audit_Report.setObservations_ii_e(observations_ii_e);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_e(management_ii_e);
		pfm_Qr_Internal_Audit_Report.setObservations_ii_f(observations_ii_f);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_f(management_ii_f);
		pfm_Qr_Internal_Audit_Report.setObservations_ii_g(observations_ii_g);
		pfm_Qr_Internal_Audit_Report.setManagement_ii_g(management_ii_g);	
		pfm_Qr_Internal_Audit_Report.setObservations_iii_a(observations_iii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_iii_a(management_iii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_iii_b(observations_iii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_iii_b(management_iii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_iii_c(observations_iii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_iii_c(management_iii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_iii_d(observations_iii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_iii_d(management_iii_d);
		pfm_Qr_Internal_Audit_Report.setObservations_iii_e(observations_iii_e);
		pfm_Qr_Internal_Audit_Report.setManagement_iii_e(management_iii_e);	
		pfm_Qr_Internal_Audit_Report.setObservations_iv_a(observations_iv_a);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_a(management_iv_a);
		pfm_Qr_Internal_Audit_Report.setObservations_iv_b(observations_iv_b);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_b(management_iv_b);
		pfm_Qr_Internal_Audit_Report.setObservations_iv_c(observations_iv_c);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_c(management_iv_c);
		pfm_Qr_Internal_Audit_Report.setObservations_iv_d(observations_iv_d);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_d(management_iv_d);
		pfm_Qr_Internal_Audit_Report.setObservations_iv_e(observations_iv_e);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_e(management_iv_e);	
		pfm_Qr_Internal_Audit_Report.setObservations_iv_f(observations_iv_f);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_f(management_iv_f);
		pfm_Qr_Internal_Audit_Report.setObservations_iv_g(observations_iv_g);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_g(management_iv_g);
		pfm_Qr_Internal_Audit_Report.setObservations_iv_h(observations_iv_h);
		pfm_Qr_Internal_Audit_Report.setManagement_iv_h(management_iv_h);
		pfm_Qr_Internal_Audit_Report.setObservations_v_a(observations_v_a);
		pfm_Qr_Internal_Audit_Report.setManagement_v_a(management_v_a);
		pfm_Qr_Internal_Audit_Report.setObservations_v_b(observations_v_b);
		pfm_Qr_Internal_Audit_Report.setManagement_v_b(management_v_b);	
		pfm_Qr_Internal_Audit_Report.setObservations_v_c(observations_v_c);
		pfm_Qr_Internal_Audit_Report.setManagement_v_c(management_v_c);
		pfm_Qr_Internal_Audit_Report.setObservations_v_d(observations_v_d);
		pfm_Qr_Internal_Audit_Report.setManagement_v_d(management_v_d);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_a(observations_vi_a);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_a(management_vi_a);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_b(observations_vi_b);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_b(management_vi_b);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_c(observations_vi_c);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_c(management_vi_c);	
		pfm_Qr_Internal_Audit_Report.setObservations_vi_d(observations_vi_d);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_d(management_vi_d);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_e(observations_vi_e);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_e(management_vi_e);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_f(observations_vi_f);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_f(management_vi_f);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_g(observations_vi_g);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_g(management_vi_g);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_h(observations_vi_h);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_h(management_vi_h);	
		pfm_Qr_Internal_Audit_Report.setObservations_vi_i(observations_vi_i);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_i(management_vi_i);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_j(observations_vi_j);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_j(management_vi_j);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_k(observations_vi_k);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_k(management_vi_k);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_l(observations_vi_l);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_l(management_vi_l);
		pfm_Qr_Internal_Audit_Report.setObservations_vi_m(observations_vi_m);
		pfm_Qr_Internal_Audit_Report.setManagement_vi_m(management_vi_m);	
		pfm_Qr_Internal_Audit_Report.setObservations_vii_a(observations_vii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_a(management_vii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_b(observations_vii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_b(management_vii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_c(observations_vii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_c(management_vii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_d(observations_vii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_d(management_vii_d);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_e(observations_vii_e);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_e(management_vii_e);	
		pfm_Qr_Internal_Audit_Report.setObservations_vii_f(observations_vii_f);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_f(management_vii_f);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_g(observations_vii_g);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_g(management_vii_g);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_h(observations_vii_h);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_h(management_vii_h);
		pfm_Qr_Internal_Audit_Report.setObservations_vii_i(observations_vii_i);
		pfm_Qr_Internal_Audit_Report.setManagement_vii_i(management_vii_i);
		pfm_Qr_Internal_Audit_Report.setObservations_viii_a(observations_viii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_viii_a(management_viii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_viii_b(observations_viii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_viii_b(management_viii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_viii_c(observations_viii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_viii_c(management_viii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_viii_d(observations_viii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_viii_d(management_viii_d);	
		pfm_Qr_Internal_Audit_Report.setObservations_ix_a(observations_ix_a);
		pfm_Qr_Internal_Audit_Report.setManagement_ix_a(management_ix_a);
		pfm_Qr_Internal_Audit_Report.setObservations_ix_b(observations_ix_b);
		pfm_Qr_Internal_Audit_Report.setManagement_ix_b(management_ix_b);
		pfm_Qr_Internal_Audit_Report.setObservations_ix_c(observations_ix_c);
		pfm_Qr_Internal_Audit_Report.setManagement_ix_c(management_ix_c);
		pfm_Qr_Internal_Audit_Report.setObservations_ix_d(observations_ix_d);
		pfm_Qr_Internal_Audit_Report.setManagement_ix_d(management_ix_d);
		pfm_Qr_Internal_Audit_Report.setObservations_x_a(observations_x_a);
		pfm_Qr_Internal_Audit_Report.setManagement_x_a(management_x_a);	
		pfm_Qr_Internal_Audit_Report.setObservations_x_b(observations_x_b);
		pfm_Qr_Internal_Audit_Report.setManagement_x_b(management_x_b);
		pfm_Qr_Internal_Audit_Report.setObservations_x_c(observations_x_c);
		pfm_Qr_Internal_Audit_Report.setManagement_x_c(management_x_c);
		pfm_Qr_Internal_Audit_Report.setObservations_x_d(observations_x_d);
		pfm_Qr_Internal_Audit_Report.setManagement_x_d(management_x_d);
		pfm_Qr_Internal_Audit_Report.setObservations_x_e(observations_x_e);
		pfm_Qr_Internal_Audit_Report.setManagement_x_e(management_x_e);
		pfm_Qr_Internal_Audit_Report.setObservations_x_f(observations_x_f);
		pfm_Qr_Internal_Audit_Report.setManagement_x_f(management_x_f); 	
		pfm_Qr_Internal_Audit_Report.setObservations_xi_a(observations_xi_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xi_a(management_xi_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xi_b(observations_xi_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xi_b(management_xi_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xii_a(observations_xii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xii_a(management_xii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xii_b(observations_xii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xii_b(management_xii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xiii_a(observations_xiii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xiii_a(management_xiii_a);	
		pfm_Qr_Internal_Audit_Report.setObservations_xiii_b(observations_xiii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xiii_b(management_xiii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xiii_c(observations_xiii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xiii_c(management_xiii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_xiv_a(observations_xiv_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xiv_a(management_xiv_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xiv_b(observations_xiv_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xiv_b(management_xiv_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xiv_c(observations_xiv_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xiv_c(management_xiv_c);	
		pfm_Qr_Internal_Audit_Report.setObservations_xv_a(observations_xv_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xv_a(management_xv_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xv_b(observations_xv_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xv_b(management_xv_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xvi_a(observations_xvi_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xvi_a(management_xvi_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xvi_b(observations_xvi_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xvi_b(management_xvi_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xvi_c(observations_xvi_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xvi_c(management_xvi_c);	
		pfm_Qr_Internal_Audit_Report.setObservations_xvii_a(observations_xvii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xvii_a(management_xvii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xvii_b(observations_xvii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xvii_b(management_xvii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_a(observations_xviii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_a(management_xviii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_b(observations_xviii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_b(management_xviii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_c(observations_xviii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_c(management_xviii_c);	
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_d(observations_xviii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_d(management_xviii_d);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_e(observations_xviii_e);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_e(management_xviii_e);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_f(observations_xviii_f);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_f(management_xviii_f);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_g(observations_xviii_g);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_g(management_xviii_g);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_h(observations_xviii_h);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_h(management_xviii_h);	
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_i(observations_xviii_i);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_i(management_xviii_i);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_j(observations_xviii_j);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_j(management_xviii_j);
		pfm_Qr_Internal_Audit_Report.setObservations_xviii_k(observations_xviii_k);
		pfm_Qr_Internal_Audit_Report.setManagement_xviii_k(management_xviii_k);
		pfm_Qr_Internal_Audit_Report.setObservations_xix_a(observations_xix_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xix_a(management_xix_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xix_b(observations_xix_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xix_b(management_xix_b);	
		pfm_Qr_Internal_Audit_Report.setObservations_xix_c(observations_xix_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xix_c(management_xix_c);
		pfm_Qr_Internal_Audit_Report.setObservations_xix_d(observations_xix_d);
		pfm_Qr_Internal_Audit_Report.setManagement_xix_d(management_xix_d);
		pfm_Qr_Internal_Audit_Report.setObservations_xix_e(observations_xix_e);
		pfm_Qr_Internal_Audit_Report.setManagement_xix_e(management_xix_e);
		pfm_Qr_Internal_Audit_Report.setObservations_xix_f(observations_xix_f);
		pfm_Qr_Internal_Audit_Report.setManagement_xix_f(management_xix_f);
		pfm_Qr_Internal_Audit_Report.setObservations_xx_a(observations_xx_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xx_a(management_xx_a);	
		pfm_Qr_Internal_Audit_Report.setObservations_xx_b(observations_xx_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xx_b(management_xx_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xx_c(observations_xx_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xx_c(management_xx_c);
		pfm_Qr_Internal_Audit_Report.setObservations_xx_d(observations_xx_d);
		pfm_Qr_Internal_Audit_Report.setManagement_xx_d(management_xx_d);
		pfm_Qr_Internal_Audit_Report.setObservations_xx_e(observations_xx_e);
		pfm_Qr_Internal_Audit_Report.setManagement_xx_e(management_xx_e);
		pfm_Qr_Internal_Audit_Report.setObservations_xxi_a(observations_xxi_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xxi_a(management_xxi_a);	
		pfm_Qr_Internal_Audit_Report.setObservations_xxi_b(observations_xxi_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xxi_b(management_xxi_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xxi_c(observations_xxi_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xxi_c(management_xxi_c);
		pfm_Qr_Internal_Audit_Report.setObservations_xxi_d(observations_xxi_d);
		pfm_Qr_Internal_Audit_Report.setManagement_xxi_d(management_xxi_d);
		pfm_Qr_Internal_Audit_Report.setObservations_xxi_e(observations_xxi_e);
		pfm_Qr_Internal_Audit_Report.setManagement_xxi_e(management_xxi_e);
		pfm_Qr_Internal_Audit_Report.setObservations_xxii_a(observations_xxii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xxii_a(management_xxii_a);	
		pfm_Qr_Internal_Audit_Report.setObservations_xxii_b(observations_xxii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xxii_b(management_xxii_b);
		pfm_Qr_Internal_Audit_Report.setObservations_xxii_c(observations_xxii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xxii_c(management_xxii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_xxii_d(observations_xxii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_xxii_d(management_xxii_d);
		pfm_Qr_Internal_Audit_Report.setObservations_xxiii_a(observations_xxiii_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xxiii_a(management_xxiii_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xxiii_b(observations_xxiii_b);
		pfm_Qr_Internal_Audit_Report.setManagement_xxiii_b(management_xxiii_b);	
		pfm_Qr_Internal_Audit_Report.setObservations_xxiii_c(observations_xxiii_c);
		pfm_Qr_Internal_Audit_Report.setManagement_xxiii_c(management_xxiii_c);
		pfm_Qr_Internal_Audit_Report.setObservations_xxiii_d(observations_xxiii_d);
		pfm_Qr_Internal_Audit_Report.setManagement_xxiii_d(management_xxiii_d);
		pfm_Qr_Internal_Audit_Report.setObservations_xxiii_e(observations_xxiii_e);
		pfm_Qr_Internal_Audit_Report.setManagement_xxiii_e(management_xxiii_e);
		pfm_Qr_Internal_Audit_Report.setObservations_xxiv_a(observations_xxiv_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xxiv_a(management_xxiv_a);
		pfm_Qr_Internal_Audit_Report.setObservations_xxv_a(observations_xxv_a);
		pfm_Qr_Internal_Audit_Report.setManagement_xxv_a(management_xxv_a);*/
		pfm_Qr_Internal_Audit_Report.setMaker_comment_details(maker_comment_details);
		pfm_Qr_Internal_Audit_Report.setCreatedon(createdon);
		pfm_Qr_Internal_Audit_Report.setCreatedby(createdby);
		pfm_Qr_Internal_Audit_Report.setReportMasterId(reportMasterId);
		pfm_Qr_Internal_Audit_Report.setAnnex_comp_certificate(annex_comp_certificate);
		pfm_Qr_Internal_Audit_Report.setExtracts_board_pdf(extracts_board_pdf);
		pfm_Qr_Internal_Audit_Report.setAnnex_comp_certificate_i(annex_comp_certificate_i);
		pfm_Qr_Internal_Audit_Report.setAnnex_comp_certificate_ii(annex_comp_certificate_ii);
		pfm_Qr_Internal_Audit_Report.setAnnex_comp_certificate_iii(annex_comp_certificate_iii);
		
		
		
		
		
		
		
		
		return pfm_Qr_Internal_Audit_ReportPersistence.update(pfm_Qr_Internal_Audit_Report);
	} 
	
	/**
	 * Update updatePfm_Qr_Internal_Audit_Report
	 * @param maker_comment_details 
	 */
	/*public Pfm_Qr_Internal_Audit_Report updatePfm_Qr_Internal_Audit_Report(long reportUploadLogId, String observations_i_a, String management_i_a, 
			String observations_i_b, String management_i_b, String observations_i_c, String management_i_c, String observations_i_e,
			String management_i_e, String observations_i_d, String management_i_d, String observations_ii_a, String management_ii_a,
			String observations_ii_b, String management_ii_b, String observations_ii_c, String management_ii_c, String observations_ii_d,
			String management_ii_d, String observations_ii_e, String management_ii_e, String observations_ii_f, String management_ii_f,
			String observations_ii_g, String management_ii_g, String observations_iii_a, String management_iii_a,
			String observations_iii_b, String management_iii_b, String observations_iii_c, String management_iii_c,
			String observations_iii_d, String management_iii_d, String observations_iii_e, String management_iii_e,
			String observations_iv_a, String management_iv_a, String observations_iv_b, String management_iv_b,
			String observations_iv_c, String management_iv_c, String observations_iv_d, String management_iv_d,
			String observations_iv_e, String management_iv_e, String observations_iv_f, String management_iv_f,
			String observations_iv_g, String management_iv_g, String observations_iv_h, String management_iv_h,
			String observations_v_a, String management_v_a, String observations_v_b, String management_v_b,
			String observations_v_c, String management_v_c, String observations_v_d, String management_v_d,
			String observations_vi_a, String management_vi_a, String observations_vi_b, String management_vi_b,
			String observations_vi_c, String management_vi_c, String observations_vi_d, String management_vi_d,
			String observations_vi_e, String management_vi_e, String observations_vi_f, String management_vi_f,
			String observations_vi_g, String management_vi_g, String observations_vi_h, String management_vi_h,
			String observations_vi_i, String management_vi_i, String observations_vi_j, String management_vi_j,
			String observations_vi_k, String management_vi_k, String observations_vi_l, String management_vi_l, 
			String observations_vi_m, String management_vi_m, String observations_vii_a, String management_vii_a,
			String observations_vii_b, String management_vii_b, String observations_vii_c, String management_vii_c,
			String observations_vii_d, String management_vii_d, String observations_vii_e, String management_vii_e,
			String observations_vii_f, String management_vii_f, String observations_viii_a, String management_viii_a,
			String observations_viii_b, String management_viii_b, String observations_viii_c, String management_viii_c,
			String observations_viii_d, String management_viii_d, String observations_ix_a, String management_ix_a,
			String observations_ix_b, String management_ix_b, String observations_ix_c, String management_ix_c,
			String observations_ix_d, String management_ix_d, String observations_x_a, String management_x_a,
			String observations_x_b, String management_x_b, String observations_x_c, String management_x_c,
			String observations_x_d, String management_x_d, String observations_x_e, String management_x_e,
			String observations_x_f, String management_x_f, String observations_xi_a, String management_xi_a,
			String observations_xi_b, String management_xi_b, String observations_xii_a, String management_xii_a,
			String observations_xii_b, String management_xii_b, String observations_xiii_a, String management_xiii_a,
			String observations_xiii_b, String management_xiii_b, String observations_xiii_c, String management_xiii_c, 
			String observations_xiv_a, String management_xiv_a, String observations_xiv_b, String management_xiv_b,
			String observations_xiv_c, String management_xiv_c, String observations_xv_a, String management_xv_a,
			String observations_xv_b, String management_xv_b, String observations_xvi_a, String management_xvi_a,
			String observations_xvi_b, String management_xvi_b, String observations_xvi_c, String management_xvi_c,
			String observations_xvii_a, String management_xvii_a, String observations_xvii_b, String management_xvii_b,
			String observations_xviii_a, String management_xviii_a, String observations_xviii_b, String management_xviii_b,
			String observations_xviii_c, String management_xviii_c, String observations_xviii_d, String management_xviii_d,
			String observations_xviii_e, String management_xviii_e, String observations_xviii_f, String management_xviii_f,
			String observations_xviii_g, String management_xviii_g, String observations_xviii_h, String management_xviii_h,
			String observations_xviii_i, String management_xviii_i, String observations_xviii_j, String management_xviii_j,
			String observations_xviii_k, String management_xviii_k, String observations_xix_a, String management_xix_a,
			String observations_xix_b, String management_xix_b, String observations_xix_c, String management_xix_c, 
			String observations_xix_d, String management_xix_d, String observations_xix_e, String management_xix_e,
			String observations_xix_f, String management_xix_f, String observations_xx_a, String management_xx_a,
			String observations_xx_b, String management_xx_b, String observations_xx_c, String management_xx_c,
			String observations_xx_d, String management_xx_d, String observations_xx_e, String management_xx_e,
			String observations_xxi_a, String management_xxi_a, String observations_xxi_b, String management_xxi_b,
			String observations_xxi_c, String management_xxi_c, String observations_xxi_d, String management_xxi_d,
			String observations_xxi_e, String management_xxi_e, String observations_xxii_a, String management_xxii_a,
			String observations_xxii_b, String management_xxii_b, String observations_xxii_c, String management_xxii_c,
			String observations_xxii_d, String management_xxii_d, String observations_xxiii_a, String management_xxiii_a,
			String observations_xxiii_b, String management_xxiii_b, String observations_xxiii_c, String management_xxiii_c,
			String observations_xxiii_d, String management_xxiii_d, String observations_xxiii_e, String management_xxiii_e,
			String observations_xxiv_a, String management_xxiv_a, String observations_xxv_a, String management_xxv_a
			)*/
		public Pfm_Qr_Internal_Audit_Report updatePfm_Qr_Internal_Audit_Report(long reportUploadLogId,long fileEntryId, String maker_comment_details,int isAmRejected)
		{
			Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.fetchByPrimaryKey(reportUploadLogId);
				
			pfm_Qr_Internal_Audit_Report.setMaker_comment_details(maker_comment_details);
			pfm_Qr_Internal_Audit_Report.setFileEntryId(fileEntryId);
			pfm_Qr_Internal_Audit_Report.setIsAmRejected(isAmRejected);
					/*pfm_Qr_Internal_Audit_Report.setObservations_i_a(observations_i_a);
					pfm_Qr_Internal_Audit_Report.setManagement_i_a(management_i_a);
					pfm_Qr_Internal_Audit_Report.setObservations_i_b(observations_i_b);
					pfm_Qr_Internal_Audit_Report.setManagement_i_b(management_i_b);	
					pfm_Qr_Internal_Audit_Report.setObservations_i_c(observations_i_c);
					pfm_Qr_Internal_Audit_Report.setManagement_i_c(management_i_c);
					pfm_Qr_Internal_Audit_Report.setObservations_i_e(observations_i_e);
					pfm_Qr_Internal_Audit_Report.setManagement_i_e(management_i_e);
					pfm_Qr_Internal_Audit_Report.setObservations_i_d(observations_i_d);
					pfm_Qr_Internal_Audit_Report.setManagement_i_d(management_i_d);
					pfm_Qr_Internal_Audit_Report.setObservations_ii_a(observations_ii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_a(management_ii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_ii_b(observations_ii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_b(management_ii_b);	
					pfm_Qr_Internal_Audit_Report.setObservations_ii_c(observations_ii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_c(management_ii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_ii_d(observations_ii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_d(management_ii_d);
					pfm_Qr_Internal_Audit_Report.setObservations_ii_e(observations_ii_e);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_e(management_ii_e);
					pfm_Qr_Internal_Audit_Report.setObservations_ii_f(observations_ii_f);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_f(management_ii_f);
					pfm_Qr_Internal_Audit_Report.setObservations_ii_g(observations_ii_g);
					pfm_Qr_Internal_Audit_Report.setManagement_ii_g(management_ii_g);	
					pfm_Qr_Internal_Audit_Report.setObservations_iii_a(observations_iii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_iii_a(management_iii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_iii_b(observations_iii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_iii_b(management_iii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_iii_c(observations_iii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_iii_c(management_iii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_iii_d(observations_iii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_iii_d(management_iii_d);
					pfm_Qr_Internal_Audit_Report.setObservations_iii_e(observations_iii_e);
					pfm_Qr_Internal_Audit_Report.setManagement_iii_e(management_iii_e);	
					pfm_Qr_Internal_Audit_Report.setObservations_iv_a(observations_iv_a);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_a(management_iv_a);
					pfm_Qr_Internal_Audit_Report.setObservations_iv_b(observations_iv_b);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_b(management_iv_b);
					pfm_Qr_Internal_Audit_Report.setObservations_iv_c(observations_iv_c);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_c(management_iv_c);
					pfm_Qr_Internal_Audit_Report.setObservations_iv_d(observations_iv_d);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_d(management_iv_d);
					pfm_Qr_Internal_Audit_Report.setObservations_iv_e(observations_iv_e);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_e(management_iv_e);	
					pfm_Qr_Internal_Audit_Report.setObservations_iv_f(observations_iv_f);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_f(management_iv_f);
					pfm_Qr_Internal_Audit_Report.setObservations_iv_g(observations_iv_g);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_g(management_iv_g);
					pfm_Qr_Internal_Audit_Report.setObservations_iv_h(observations_iv_h);
					pfm_Qr_Internal_Audit_Report.setManagement_iv_h(management_iv_h);
					pfm_Qr_Internal_Audit_Report.setObservations_v_a(observations_v_a);
					pfm_Qr_Internal_Audit_Report.setManagement_v_a(management_v_a);
					pfm_Qr_Internal_Audit_Report.setObservations_v_b(observations_v_b);
					pfm_Qr_Internal_Audit_Report.setManagement_v_b(management_v_b);	
					pfm_Qr_Internal_Audit_Report.setObservations_v_c(observations_v_c);
					pfm_Qr_Internal_Audit_Report.setManagement_v_c(management_v_c);
					pfm_Qr_Internal_Audit_Report.setObservations_v_d(observations_v_d);
					pfm_Qr_Internal_Audit_Report.setManagement_v_d(management_v_d);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_a(observations_vi_a);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_a(management_vi_a);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_b(observations_vi_b);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_b(management_vi_b);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_c(observations_vi_c);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_c(management_vi_c);	
					pfm_Qr_Internal_Audit_Report.setObservations_vi_d(observations_vi_d);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_d(management_vi_d);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_e(observations_vi_e);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_e(management_vi_e);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_f(observations_vi_f);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_f(management_vi_f);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_g(observations_vi_g);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_g(management_vi_g);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_h(observations_vi_h);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_h(management_vi_h);	
					pfm_Qr_Internal_Audit_Report.setObservations_vi_i(observations_vi_i);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_i(management_vi_i);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_j(observations_vi_j);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_j(management_vi_j);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_k(observations_vi_k);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_k(management_vi_k);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_l(observations_vi_l);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_l(management_vi_l);
					pfm_Qr_Internal_Audit_Report.setObservations_vi_m(observations_vi_m);
					pfm_Qr_Internal_Audit_Report.setManagement_vi_m(management_vi_m);	
					pfm_Qr_Internal_Audit_Report.setObservations_vii_a(observations_vii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_vii_a(management_vii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_vii_b(observations_vii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_vii_b(management_vii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_vii_c(observations_vii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_vii_c(management_vii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_vii_d(observations_vii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_vii_d(management_vii_d);
					pfm_Qr_Internal_Audit_Report.setObservations_vii_e(observations_vii_e);
					pfm_Qr_Internal_Audit_Report.setManagement_vii_e(management_vii_e);	
					pfm_Qr_Internal_Audit_Report.setObservations_vii_f(observations_vii_f);
					pfm_Qr_Internal_Audit_Report.setManagement_vii_f(management_vii_f);
					pfm_Qr_Internal_Audit_Report.setObservations_viii_a(observations_viii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_viii_a(management_viii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_viii_b(observations_viii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_viii_b(management_viii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_viii_c(observations_viii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_viii_c(management_viii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_viii_d(observations_viii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_viii_d(management_viii_d);	
					pfm_Qr_Internal_Audit_Report.setObservations_ix_a(observations_ix_a);
					pfm_Qr_Internal_Audit_Report.setManagement_ix_a(management_ix_a);
					pfm_Qr_Internal_Audit_Report.setObservations_ix_b(observations_ix_b);
					pfm_Qr_Internal_Audit_Report.setManagement_ix_b(management_ix_b);
					pfm_Qr_Internal_Audit_Report.setObservations_ix_c(observations_ix_c);
					pfm_Qr_Internal_Audit_Report.setManagement_ix_c(management_ix_c);
					pfm_Qr_Internal_Audit_Report.setObservations_ix_d(observations_ix_d);
					pfm_Qr_Internal_Audit_Report.setManagement_ix_d(management_ix_d);
					pfm_Qr_Internal_Audit_Report.setObservations_x_a(observations_x_a);
					pfm_Qr_Internal_Audit_Report.setManagement_x_a(management_x_a);	
					pfm_Qr_Internal_Audit_Report.setObservations_x_b(observations_x_b);
					pfm_Qr_Internal_Audit_Report.setManagement_x_b(management_x_b);
					pfm_Qr_Internal_Audit_Report.setObservations_x_c(observations_x_c);
					pfm_Qr_Internal_Audit_Report.setManagement_x_c(management_x_c);
					pfm_Qr_Internal_Audit_Report.setObservations_x_d(observations_x_d);
					pfm_Qr_Internal_Audit_Report.setManagement_x_d(management_x_d);
					pfm_Qr_Internal_Audit_Report.setObservations_x_e(observations_x_e);
					pfm_Qr_Internal_Audit_Report.setManagement_x_e(management_x_e);
					pfm_Qr_Internal_Audit_Report.setObservations_x_f(observations_x_f);
					pfm_Qr_Internal_Audit_Report.setManagement_x_f(management_x_f); 	
					pfm_Qr_Internal_Audit_Report.setObservations_xi_a(observations_xi_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xi_a(management_xi_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xi_b(observations_xi_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xi_b(management_xi_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xii_a(observations_xii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xii_a(management_xii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xii_b(observations_xii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xii_b(management_xii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xiii_a(observations_xiii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xiii_a(management_xiii_a);	
					pfm_Qr_Internal_Audit_Report.setObservations_xiii_b(observations_xiii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xiii_b(management_xiii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xiii_c(observations_xiii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xiii_c(management_xiii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_xiv_a(observations_xiv_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xiv_a(management_xiv_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xiv_b(observations_xiv_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xiv_b(management_xiv_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xiv_c(observations_xiv_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xiv_c(management_xiv_c);	
					pfm_Qr_Internal_Audit_Report.setObservations_xv_a(observations_xv_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xv_a(management_xv_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xv_b(observations_xv_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xv_b(management_xv_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xvi_a(observations_xvi_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xvi_a(management_xvi_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xvi_b(observations_xvi_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xvi_b(management_xvi_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xvi_c(observations_xvi_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xvi_c(management_xvi_c);	
					pfm_Qr_Internal_Audit_Report.setObservations_xvii_a(observations_xvii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xvii_a(management_xvii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xvii_b(observations_xvii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xvii_b(management_xvii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_a(observations_xviii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_a(management_xviii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_b(observations_xviii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_b(management_xviii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_c(observations_xviii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_c(management_xviii_c);	
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_d(observations_xviii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_d(management_xviii_d);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_e(observations_xviii_e);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_e(management_xviii_e);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_f(observations_xviii_f);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_f(management_xviii_f);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_g(observations_xviii_g);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_g(management_xviii_g);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_h(observations_xviii_h);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_h(management_xviii_h);	
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_i(observations_xviii_i);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_i(management_xviii_i);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_j(observations_xviii_j);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_j(management_xviii_j);
					pfm_Qr_Internal_Audit_Report.setObservations_xviii_k(observations_xviii_k);
					pfm_Qr_Internal_Audit_Report.setManagement_xviii_k(management_xviii_k);
					pfm_Qr_Internal_Audit_Report.setObservations_xix_a(observations_xix_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xix_a(management_xix_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xix_b(observations_xix_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xix_b(management_xix_b);	
					pfm_Qr_Internal_Audit_Report.setObservations_xix_c(observations_xix_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xix_c(management_xix_c);
					pfm_Qr_Internal_Audit_Report.setObservations_xix_d(observations_xix_d);
					pfm_Qr_Internal_Audit_Report.setManagement_xix_d(management_xix_d);
					pfm_Qr_Internal_Audit_Report.setObservations_xix_e(observations_xix_e);
					pfm_Qr_Internal_Audit_Report.setManagement_xix_e(management_xix_e);
					pfm_Qr_Internal_Audit_Report.setObservations_xix_f(observations_xix_f);
					pfm_Qr_Internal_Audit_Report.setManagement_xix_f(management_xix_f);
					pfm_Qr_Internal_Audit_Report.setObservations_xx_a(observations_xx_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xx_a(management_xx_a);	
					pfm_Qr_Internal_Audit_Report.setObservations_xx_b(observations_xx_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xx_b(management_xx_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xx_c(observations_xx_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xx_c(management_xx_c);
					pfm_Qr_Internal_Audit_Report.setObservations_xx_d(observations_xx_d);
					pfm_Qr_Internal_Audit_Report.setManagement_xx_d(management_xx_d);
					pfm_Qr_Internal_Audit_Report.setObservations_xx_e(observations_xx_e);
					pfm_Qr_Internal_Audit_Report.setManagement_xx_e(management_xx_e);
					pfm_Qr_Internal_Audit_Report.setObservations_xxi_a(observations_xxi_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xxi_a(management_xxi_a);	
					pfm_Qr_Internal_Audit_Report.setObservations_xxi_b(observations_xxi_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xxi_b(management_xxi_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xxi_c(observations_xxi_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xxi_c(management_xxi_c);
					pfm_Qr_Internal_Audit_Report.setObservations_xxi_d(observations_xxi_d);
					pfm_Qr_Internal_Audit_Report.setManagement_xxi_d(management_xxi_d);
					pfm_Qr_Internal_Audit_Report.setObservations_xxi_e(observations_xxi_e);
					pfm_Qr_Internal_Audit_Report.setManagement_xxi_e(management_xxi_e);
					pfm_Qr_Internal_Audit_Report.setObservations_xxii_a(observations_xxii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xxii_a(management_xxii_a);	
					pfm_Qr_Internal_Audit_Report.setObservations_xxii_b(observations_xxii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xxii_b(management_xxii_b);
					pfm_Qr_Internal_Audit_Report.setObservations_xxii_c(observations_xxii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xxii_c(management_xxii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_xxii_d(observations_xxii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_xxii_d(management_xxii_d);
					pfm_Qr_Internal_Audit_Report.setObservations_xxiii_a(observations_xxiii_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xxiii_a(management_xxiii_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xxiii_b(observations_xxiii_b);
					pfm_Qr_Internal_Audit_Report.setManagement_xxiii_b(management_xxiii_b);	
					pfm_Qr_Internal_Audit_Report.setObservations_xxiii_c(observations_xxiii_c);
					pfm_Qr_Internal_Audit_Report.setManagement_xxiii_c(management_xxiii_c);
					pfm_Qr_Internal_Audit_Report.setObservations_xxiii_d(observations_xxiii_d);
					pfm_Qr_Internal_Audit_Report.setManagement_xxiii_d(management_xxiii_d);
					pfm_Qr_Internal_Audit_Report.setObservations_xxiii_e(observations_xxiii_e);
					pfm_Qr_Internal_Audit_Report.setManagement_xxiii_e(management_xxiii_e);
					pfm_Qr_Internal_Audit_Report.setObservations_xxiv_a(observations_xxiv_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xxiv_a(management_xxiv_a);
					pfm_Qr_Internal_Audit_Report.setObservations_xxv_a(observations_xxv_a);
					pfm_Qr_Internal_Audit_Report.setManagement_xxv_a(management_xxv_a);*/
					
					return pfm_Qr_Internal_Audit_ReportPersistence.update(pfm_Qr_Internal_Audit_Report);
		} 
	
	
		public Pfm_Qr_Internal_Audit_Report updateReportUploadLog(Date createdon, long createdBy, long fileEntryId, boolean uploaded, long reportUploadLogId, int status, long statusByUserId, 
				String statusByUserName, Date statusDate, ServiceContext serviceContext, String remarks, boolean reupload, long createdby) {
			Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = null;
			try {
				pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportLocalService.getPfm_Qr_Internal_Audit_Report(reportUploadLogId);
			} catch (PortalException e) {
				// _log.error(e);
			}
			
			ReportUploadLog  reportUploadLog = reportUploadLogLocalService.fetchReportUploadLog(reportUploadLogId);
			if(reupload) {		
				pfm_Qr_Internal_Audit_Report.setReportMasterId(reportUploadLog.getReportMasterId());
				pfm_Qr_Internal_Audit_Report.setReportDate(reportUploadLog.getReportDate());
				pfm_Qr_Internal_Audit_Report.setCreatedon(createdon);
				pfm_Qr_Internal_Audit_Report.setCreatedby(createdBy);
				pfm_Qr_Internal_Audit_Report.setStatus(status);
				pfm_Qr_Internal_Audit_Report.setStatusByUserId(statusByUserId);
				pfm_Qr_Internal_Audit_Report.setStatusByUserName(statusByUserName);
				pfm_Qr_Internal_Audit_Report.setStatusDate(statusDate);
				pfm_Qr_Internal_Audit_Report.setRemarks(remarks);
				pfm_Qr_Internal_Audit_Report.setFileEntryId(fileEntryId);
				
				pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.update(pfm_Qr_Internal_Audit_Report);
				//reportUploadLog.setUploaded(true);
				/*reportUploadLog.setUploaded_i(1);*/
				
				_log.info(reportUploadLogLocalService.updateReportUploadLog(1, reportUploadLogId));
				//reportUploadLogPersistence.flush();
				reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
				createAssetForPfm_Qr_Internal_Audit_Report(pfm_Qr_Internal_Audit_Report, serviceContext, createdBy);
			}else {
				pfm_Qr_Internal_Audit_Report.setCreatedon(createdon);
				pfm_Qr_Internal_Audit_Report.setCreatedby(createdby);
				
				pfm_Qr_Internal_Audit_Report.setStatus(status);
				pfm_Qr_Internal_Audit_Report.setStatusByUserId(statusByUserId);
				pfm_Qr_Internal_Audit_Report.setStatusByUserName(statusByUserName);
				pfm_Qr_Internal_Audit_Report.setStatusDate(statusDate);
				pfm_Qr_Internal_Audit_Report.setRemarks(remarks);
				pfm_Qr_Internal_Audit_Report.setFileEntryId(fileEntryId);
				
				pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.update(pfm_Qr_Internal_Audit_Report);
				//reportUploadLog.setUploaded(true);
				reportUploadLog.setUploaded_i(1);
				
				_log.info(reportUploadLogPersistence.update(reportUploadLog));
				reportUploadFileLogLocalService.addReportUploadFileLog(reportUploadLogId, fileEntryId, createdBy);
				String workflowContext = pfm_Qr_Internal_Audit_Report.getWorkflowContext();
				try {
					workflowAsignment(serviceContext.getCompanyId(), createdBy, "", workflowContext, reportUploadLogId, serviceContext);
				} catch (PortalException e) {
					 _log.error(e);
				}
				
				
			}
			
			
			return pfm_Qr_Internal_Audit_Report;
		}
		
		public void createAssetForPfm_Qr_Internal_Audit_Report(Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report, ServiceContext serviceContext, long CreatedBy) {
			
			
			if(Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
				AssetEntry assetEntry = null;
				try {
					assetEntry = AssetEntryLocalServiceUtil.updateEntry(CreatedBy,
		                    serviceContext.getScopeGroupId(),
		                    new Date(),
		                    new Date(),
		                    Pfm_Qr_Internal_Audit_Report.class.getName(),
		                    pfm_Qr_Internal_Audit_Report.getReportUploadLogId(),
		                    pfm_Qr_Internal_Audit_Report.getUuid(),
		                    0,
		                    null,
		                    null,
		                    true,
		                    false,
		                    new Date(),
		                    null,
		                    new Date(),
		                    null,
		                    ContentTypes.TEXT_HTML,
		                    "Report Upload Log Asset",
		                    "Report Upload Log with Id: " +  pfm_Qr_Internal_Audit_Report.getReportUploadLogId(),
		                    null,
		                    null,
		                    null,
		                    0,
		                    0,
		                    null);
	
					Indexer<Pfm_Qr_Internal_Audit_Report> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Pfm_Qr_Internal_Audit_Report.class);
					indexer.reindex(pfm_Qr_Internal_Audit_Report);
	
					WorkflowHandlerRegistryUtil.startWorkflowInstance(serviceContext.getCompanyId(), serviceContext.getScopeGroup().getGroupId(), CreatedBy, 
							Pfm_Qr_Internal_Audit_Report.class.getName(), pfm_Qr_Internal_Audit_Report.getReportUploadLogId(), pfm_Qr_Internal_Audit_Report, serviceContext);
					
					
	
				} catch (Exception e) {
					 _log.error(e);
				}
			}	
			
		}
		
		public Pfm_Qr_Internal_Audit_Report updatePfm_Qr_Internal_Audit_ReportStatus(long id, long userId, int status, ServiceContext serviceContext, String workflowContext) {
			Pfm_Qr_Internal_Audit_Report pfm_Qr_Internal_Audit_Report = null;
			try {
				pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.fetchByPrimaryKey(id);
			} catch (Exception e) {
				 _log.error(e);
			}
	
			if (Validator.isNotNull(pfm_Qr_Internal_Audit_Report)) {
				pfm_Qr_Internal_Audit_Report.setStatus(status);
				pfm_Qr_Internal_Audit_Report.setStatusByUserId(userId);
				pfm_Qr_Internal_Audit_Report.setStatusDate(new Date());
				pfm_Qr_Internal_Audit_Report.setWorkflowContext(workflowContext);
	
				User user;
	
				try {
					user = userLocalService.getUser(userId);
					pfm_Qr_Internal_Audit_Report.setStatusByUserName(user.getFullName());
					pfm_Qr_Internal_Audit_Report.setStatusByUserUuid(user.getUserUuid());
				} catch (PortalException e) {
					 _log.error(e);
				}
	
				pfm_Qr_Internal_Audit_Report = pfm_Qr_Internal_Audit_ReportPersistence.update(pfm_Qr_Internal_Audit_Report);
			}
	
			try {
				if (status == WorkflowConstants.STATUS_APPROVED) {
					// Update the asset status to visibile
					assetEntryLocalService.updateEntry(Pfm_Qr_Internal_Audit_Report.class.getName(), id, new Date(), null, true, true);
				} else {
					// Set applicationRegistration entity status to false
					assetEntryLocalService.updateVisible(Pfm_Qr_Internal_Audit_Report.class.getName(), id, false);
				}
			} catch (Exception e) {
				 _log.error(e);
			}
	
			return pfm_Qr_Internal_Audit_Report;
		}
		
		public void workflowAsignment(long companyId, long userId, String comment, String json, long assetClassPK, ServiceContext serviceContext) throws PortalException {
			
			String transitionName = "Reupload";
			Map<String, Serializable> workflowContext =  WorkflowContextUtil.convert(json);
			
			OrderByComparator<KaleoInstance> orderByComparator= OrderByComparatorFactoryUtil.create("KALEOINSTANCE", "kaleoInstanceId", false);
			List<KaleoInstance> kaleoInstances = KaleoInstanceLocalServiceUtil.getKaleoInstances(userId, Pfm_Qr_Internal_Audit_Report.class.getName(), assetClassPK, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator, serviceContext);
			//KaleoInstanceToken kaleoInstanceToken =KaleoInstanceTokenLocalServiceUtil.getRootKaleoInstanceToken(kaleoInstances.get(0).getKaleoInstanceId(), workflowContext, serviceContext);
			
			//long workflowTaskId = kaleoInstanceToken.getPrimaryKey();
			long workflowInstanceId = kaleoInstances.get(0).getKaleoInstanceId();
			OrderByComparator<WorkflowTask> orderByComparator2 =  WorkflowComparatorFactoryUtil.getTaskModifiedDateComparator();
			List<WorkflowTask> workflowTasks =  WorkflowTaskManagerUtil.getWorkflowTasksByWorkflowInstance(companyId, userId, workflowInstanceId , false,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator2 );
			long workflowTaskId = workflowTasks.get(0).getWorkflowTaskId();
			WorkflowTaskManagerUtil.completeWorkflowTask(companyId, userId, workflowTaskId, transitionName, comment, workflowContext);
			
		}
		Log _log = LogFactoryUtil.getLog(Pfm_Qr_Internal_Audit_ReportLocalServiceImpl.class);
		
		public long countPfm_Qr_Internal_Audit_ReportByIdsAndStatus(List<Long> ids, int status){
			DynamicQuery query = DynamicQueryFactoryUtil.forClass(Pfm_Qr_Internal_Audit_ReportImpl.class, PortalClassLoaderUtil.getClassLoader());
			query.add(RestrictionsFactoryUtil.in("reportUploadLogId", ids));
			query.add(RestrictionsFactoryUtil.eq("status", status));
			return pfm_Qr_Internal_Audit_ReportLocalService.dynamicQueryCount(query);
		}
		
		public long countByReportMasterIdsAndStatus(List<Long> ids,int status){
			DynamicQuery query = DynamicQueryFactoryUtil.forClass(Pfm_Qr_Internal_Audit_ReportImpl.class, PortalClassLoaderUtil.getClassLoader());
			query.add(RestrictionsFactoryUtil.eq("status", status));
			query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
			
			return pfm_Qr_Internal_Audit_ReportLocalService.dynamicQueryCount(query);
		}
		
		public List<Pfm_Qr_Internal_Audit_Report> getByReportMasterIdsAndStatus(List<Long> ids,int status){
			DynamicQuery query = DynamicQueryFactoryUtil.forClass(Pfm_Qr_Internal_Audit_ReportImpl.class, PortalClassLoaderUtil.getClassLoader());
			query.add(RestrictionsFactoryUtil.eq("status", status));
			query.add(RestrictionsFactoryUtil.in("reportMasterId", ids));
			
			return pfm_Qr_Internal_Audit_ReportLocalService.dynamicQuery(query);
		}
	}