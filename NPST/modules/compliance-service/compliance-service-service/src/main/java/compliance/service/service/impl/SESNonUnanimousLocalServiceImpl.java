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

package compliance.service.service.impl;

import java.util.Date;

import compliance.service.model.SESNonUnanimous;
import compliance.service.service.base.SESNonUnanimousLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class SESNonUnanimousLocalServiceImpl extends SESNonUnanimousLocalServiceBaseImpl {
	
	public SESNonUnanimous addSesUnanimous(String companyName, Date meetingDate, String resolution, 
			String votedFor, String votedAbstain, String votedAgainst, String finalVote) {
		
		SESNonUnanimous sesNonUnanimous = sesNonUnanimousPersistence.create(counterLocalService.increment());
		sesNonUnanimous.setCompany_name(companyName);
		sesNonUnanimous.setMeeting_date(meetingDate);
		sesNonUnanimous.setResolution(resolution);
		sesNonUnanimous.setVoted_for(votedFor);
		sesNonUnanimous.setVoted_abstain(votedAbstain);
		sesNonUnanimous.setVoted_against(votedAgainst);
		sesNonUnanimous.setFinal_vote(finalVote);
		
		sesNonUnanimous = sesNonUnanimousPersistence.update(sesNonUnanimous);
		
		return sesNonUnanimous;
		
	}
	
	
}