package com.npst.file.wrapper;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceWrapper;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author VishalKumar
 */
@Component(
	immediate = true,
	property = {
	},
	service = ServiceWrapper.class
)
public class DLFileServiceWrapper extends DLFileEntryLocalServiceWrapper {

	public DLFileServiceWrapper() {
		super(null);
	}
	

	
	
	//DLFileEntryLocalServiceWrapper
	
	
	@Override
		public List<DLFileEntry> getFileEntries(long groupId, long folderId) {
			// TODO Auto-generated method stub
		log.info("DLFIle service method:   1");
			return super.getFileEntries(groupId, folderId);
		}
	@Override
	public List<DLFileEntry> getFileEntries(long folderId, String name) {
		// TODO Auto-generated method stub
		log.info("DLFIle service method:   2");
		return super.getFileEntries(folderId, name);
	}
	
	@Override
	public List<DLFileEntry> getFileEntries(long groupId, long folderId, int start, int end,
			OrderByComparator<DLFileEntry> orderByComparator) {
		// TODO Auto-generated method stub
		log.info("DLFIle service method:   3");
		return super.getFileEntries(groupId, folderId, start, end, orderByComparator);
	}
	
	@Override
	public List<DLFileEntry> getFileEntries(long groupId, long folderId, int status, int start, int end,
			OrderByComparator<DLFileEntry> orderByComparator) {
		// TODO Auto-generated method stub
		log.info("DLFIle service method:   4");
		return super.getFileEntries(groupId, folderId, status, start, end, orderByComparator);
	}
	
	@Override
	public List<DLFileEntry> getFileEntries(long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
			QueryDefinition<DLFileEntry> queryDefinition) {
		// TODO Auto-generated method stub
		log.info("DLFIle service method:   5");
		return super.getFileEntries(groupId, userId, folderIds, mimeTypes, queryDefinition);
	}
	
	@Override
	public List<DLFileEntry> getFileEntries(long groupId, long userId, List<Long> repositoryIds, List<Long> folderIds,
			String[] mimeTypes, QueryDefinition<DLFileEntry> queryDefinition) {
		// TODO Auto-generated method stub
		log.info("DLFIle service method:   6");
		return super.getFileEntries(groupId, userId, repositoryIds, folderIds, mimeTypes, queryDefinition);
	}
	
	@Override
	public List<DLFileEntry> getFileEntries(int start, int end) {
		// TODO Auto-generated method stub
		log.info("DLFIle service method:   7");
		return super.getFileEntries(start, end);
	}

	
//	public List<DLFileEntryType> getFolderFileEntryTypes(long[] groupIds, long folderId, boolean inherited)
//			throws PortalException {
//		//return super.getFolderFileEntryTypes(groupIds, folderId, inherited).stream().sorted(Comparator.comparing(DLFileEntryType::getName)).collect(Collectors.toList());
//	return null;
//	}
//	
//		public List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
//		//	return super.getFileEntryTypes(groupIds).stream().sorted(Comparator.comparing(DLFileEntryType::getName)).collect(Collectors.toList());
//			return null;
//		}
	
	


private static Log log=LogFactoryUtil.getLog(DLFileEntryLocalServiceWrapper.class.getName());
}