<div id="previewModal" class="modal fade" tabindex="-1" role="dialog"
    aria-labelledby="previewModalTitle" aria-hidden="true" data-backdrop="static" data-keyboard="false" style="display: none;">
    <div class="modal-dialog modal-dialog-centered" role="document">
       
            <div class="modal-content">
                <div class="modal-header pb-2">
                    <div class="modal-title row">
                        <h4 class="col-sm-10">File Preview</h4>
                        <a class="col-sm-2 text-right pr-4" id="download-link" href=""><i class="fa fa-download" aria-hidden="true"></i> Download</a>
                    </div>
                    <button aria-labelledby="close" class="close previewModalClose" data-dismiss="modal" role="button" type="button">
                        <svg class="text-secondary lexicon-icon lexicon-icon-times" focusable="false"
                            role="presentation" alt="closeIcon">
                            <use href="${themeDisplay.getPathThemeImages() }/clay/icons.svg#times"></use>
                        </svg>
                    </button>
                </div>
                
                <div class="modal-body">
                	<div class="file-preview-section hide">
                    	
                    </div>
                    <div class="alert alert-danger hide" id="no-preview-error">
                    	<p>No preview generated. <span id="error-message"></span> </p>
                    </div>
                </div>
            </div>
        
    </div>
</div>

<style>
#previewModal .modal-dialog{width: 100%;}
.modal-dialog-centered .modal-dialog, .modal-dialog-centered.modal-dialog {min-height: calc(100% - 0.5rem);}
</style>