<?php
/*************************************************************
 * Created on  5 may 2011
 * Updated on  5 may 2011
 *
 * Page for administrating companies with tabs
 *
 * Created by Andreas Sehr
**************************************************************/

class page_user extends page_template {
    
    public function __construct() {
        $this->m_menu = array(
            "info" => "Company info",
            "reports" => "Reports");
        $this->m_default_page = "info";
        $this->m_class_name = "page_user";
    }

    static function info() {
        $user = new User($_REQUEST['id']);
        return '<div class="block" align="left">
                <h1><b>'.$user->name.'</b> information</h1>'.
                '<div style="float: right">'.$user->edit_user_link.'</div>'.
                $user->getInfoTable().'</div>';
    }

    static function reports() {
        $company = new User($_REQUEST['id']);
        return '<div class="block" align="left">
                <h1>Reports</h1>
                <div style="float: right">'.$company->new_report_link.'</div>'.
                $company->getReportTable().'</div>';
    }
}
?>
