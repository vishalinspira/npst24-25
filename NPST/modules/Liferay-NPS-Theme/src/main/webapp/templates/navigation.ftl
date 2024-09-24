<#--  <nav aria-label="<@liferay.language key="site-pages" />" class="${nav_css_class} active shadow" id="navigation" role="navigation">  -->
<div class="deshbordSidebar">
	<div class="sidebarContent">
		<ul role="menubar" class="sidebarList">
			<#list nav_items as nav_item>
						<#assign
							nav_item_attr_has_popup = ""
							nav_item_attr_selected = ""
							nav_item_css_class = ""
							nav_item_dropdown = ""
							nav_a_css_class = ""
							nav_a_dropdown_toggle = ""
							nav_span_dropdown_toggle = ""
							nav_item_layout = nav_item.getLayout()
						/>
					
					<#assign nav_item_layout = nav_item.getLayout() />

					<#if nav_item.isSelected()>
						<#assign
							nav_item_attr_has_popup = "aria-haspopup='true'"
							nav_item_attr_selected = "aria-selected='true'"
							nav_item_css_class = "selected active"
						/>
					</#if>
					<#if nav_item.hasChildren()>
						<#assign 
							nav_item_dropdown = "dropdown"
							nav_a_css_class = "dropdown-toggle"
							nav_a_dropdown_toggle = "aria-expanded='false'"
							nav_span_dropdown_toggle = "<i class='fas fa-angle-right nav-right-arrow'></i>"							
						/>
					</#if>

				
				<li ${nav_item_attr_selected} class="${nav_item_css_class} submenuDropdown" id="layout_${nav_item.getLayoutId()}" role="presentation" title="${nav_item.getName()}">
					<a id="submenuClick" aria-labelledby="layout_${nav_item.getLayoutId()}" class="${nav_a_css_class} text-decoration-none" ${nav_item_attr_has_popup} data-toggle="${nav_item_dropdown}" ${nav_a_dropdown_toggle} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem">
						<span class="icon"><@liferay_theme["layout-icon"] layout=nav_item_layout /> <span class="url-link">${nav_item.getName()}</span></span><span> ${nav_span_dropdown_toggle} </span>
					</a> 

					<#if nav_item.hasChildren()>
						<ul role="menu" class="submenuList">
							<#list nav_item.getChildren() as nav_child>
								<#assign
									nav_child_css_class = ""
								/>

								<#if nav_item.isSelected()>
									<#assign
										nav_child_css_class = "selected"
									/>
								</#if>

								<li class="${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}" role="presentation">
									<a aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem" class="text-body text-decoration-none">
										${nav_child.getName()}
									</a>
								</li>
							</#list>
						</ul>
					</#if>
				</li>
			</#list>
		</ul> 
	</div>
</div>
<#--  </nav>  -->