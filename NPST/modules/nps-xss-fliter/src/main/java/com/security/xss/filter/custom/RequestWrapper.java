package com.security.xss.filter.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {

	public RequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
        if (value == null) {
               return null;
                }
        return stripXSS(value);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> rawMap = super.getParameterMap();
		Map<String, String[]> filteredMap = new HashMap<String, String[]>(rawMap.size());
		Set<String> keys = rawMap.keySet();
		for (String key : keys) {
			String[] rawValue = rawMap.get(key);
			int count = rawValue.length;
			String[] filteredValue = new String[count];
			for (int i = 0; i < count; i++) {
				filteredValue[i] = stripXSS(rawValue[i]);
      }
			filteredMap.put(key, filteredValue);
		}
		return filteredMap;
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
	      if (values==null)  {
	                  return null;
	          }
	      int count = values.length;
	      String[] encodedValues = new String[count];
	      for (int i = 0; i < count; i++) {
	                 encodedValues[i] = stripXSS(values[i]);
	       }
	      return encodedValues;
	}

	private String cleanXSS(String value) {
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
	 @Override
	    public String getHeader(String name) {
	        String value = super.getHeader(name);
	        return stripXSS(value);
	    }
	 
	 private static Pattern[] patterns = new Pattern[]{
		        // Script fragments
		        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
		        // src='...'
		        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // lonely script tags
		        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
		        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // eval(...)
		        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // expression(...)
		        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
		        // javascript:...
		        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
		        // vbscript:...
		        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
		        // onload(...)=...
		        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
		    };
	
	 private String stripXSS(String value) {
	        if (value != null) {
	            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
	            // avoid encoded attacks.
	            // value = ESAPI.encoder().canonicalize(value);

	            // Avoid null characters
	            value = value.replaceAll("\0", "");

	            // Remove all sections that match a pattern
	            for (Pattern scriptPattern : patterns){
	                value = scriptPattern.matcher(value).replaceAll("");
	            }
	        }
	        return value;
	    }

}
