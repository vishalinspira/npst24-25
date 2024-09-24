package com.npst.file.wrapper;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class DLAppServiceWrapper extends com.liferay.document.library.kernel.service.DLAppServiceWrapper {
		

	
	
	public DLAppServiceWrapper() {
		super(null);
		// TODO Auto-generated constructor stub
	}

@Override
public List<Object> getFoldersAndFileEntriesAndFileShortcuts(long repositoryId, long folderId, int status,
		boolean includeMountFolders, int start, int end, OrderByComparator<?> orderByComparator)
		throws PortalException {
	log.info("DLAppServiceWrapper  method:   ");
	List<Object> objects=new ArrayList<Object>();
	List<FileEntry> fileObjects=new ArrayList<FileEntry>();
	List<DLFileEntry> dlFileEntries=new ArrayList<DLFileEntry>();
	Map<Long,FileEntry>fileEntryMap=new  HashMap<Long, FileEntry>();
	for(Object obj:super.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, includeMountFolders, start, end,
			orderByComparator)) {
		try {
			boolean isFileInstance=obj.getClass().getName().equalsIgnoreCase("com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry");
			log.debug("isFileInstance: "+isFileInstance);
			if(isFileInstance) {
				FileEntry entry=(FileEntry)obj;
				DLFileEntry dlfIle= DLFileEntryLocalServiceUtil.getDLFileEntry(entry.getFileEntryId());
				fileEntryMap.put(entry.getFileEntryId(), entry);
				dlFileEntries.add(dlfIle);
			}else {
				objects.add(obj);
			}
		}catch (Exception e) {
			log.error("error:  "+e.getMessage());
		}
		
	}
	dlFileEntries=dlFileEntries.stream().sorted(Comparator.comparing(DLFileEntry::getFileEntryTypeId).reversed()).collect(Collectors.toList());
	for(DLFileEntry dlFileEntry:dlFileEntries) {
		try {
		//FileEntry fileEntry=DLAppLocalServiceUtil.getFileEntry(dlFileEntry.getFileEntryId());
		//fileObjects.add(fileEntry);
			fileObjects.add(fileEntryMap.get(dlFileEntry.getFileEntryId()));
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	objects.addAll(fileObjects);
	return objects;
	//return super.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, includeMountFolders, start, end,orderByComparator);
}


		private static Log log=LogFactoryUtil.getLog(DLAppServiceWrapper.class.getName());
}
