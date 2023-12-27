
export interface IUserList  {
  user_id : number,
  name: string
}


export interface IUserData {
    n_id_usuario? : string,
    n_hist_calif?: string
    n_vmatriz?: string
    n_bench?: string
    n_catpan?: string
    s_tel?: string
    n_catal?: string
    s_puesto?: string
    n_ctlr_int?: string
    n_guber?: string
    n_reuters?: string
    n_notasdeuda?: string
    n_deriv?: string
    n_der_valrisk?: string
    n_corpo?: string
    n_crv_der?: string
    n_cau?: string
    n_calif?: string
    n_pro_dev?: string
    n_reginv?: string
    n_catcau?: string
    n_inter?: string
    n_betas?: string
    s_area?: string
    n_envios?: string
    n_notas_est?: string
    s_pass?: string
    n_usr?: string
    n_corptrc?: string
    s_usuario?: string
    n_panam?: string
    n_users_web?: string
    n_mensajeria?: string
    n_tasas?: string
    n_cr?: string
    s_mail?: string
    n_hist_vec?: string
    n_catcal?: string
    n_acci?: string
    n_corp_val?: string
    n_caumo?: string
    s_nombre?: string
    n_broker?: string
    n_tipcam?: string
  }

  export const InitialUser : IUserData = {
    n_id_usuario : "",
    n_hist_calif: '',
    n_vmatriz : '',
    n_bench: '',
    n_catpan : '',
    s_tel: '',
    n_catal: '',
    s_puesto: '',
    n_ctlr_int: '',
    n_guber: '',
    n_reuters: '',
    n_notasdeuda: '',
    n_deriv: '',
    n_der_valrisk: '',
    n_corpo: '',
    n_crv_der: '',
    n_cau: '',
    n_calif: '',
    n_pro_dev: '',
    n_reginv: '',
    n_catcau: '',
    n_inter: '',
    n_betas: '',
    s_area: '',
    n_envios: '',
    n_notas_est: '',
    s_pass: '',
    n_usr: '',
    n_corptrc: '',
    s_usuario: '',
    n_panam: '',
    n_users_web: '',
    n_mensajeria: '',
    n_tasas: '',
    n_cr: '',
    s_mail: '',
    n_hist_vec: '',
    n_catcal: '',
    n_acci: '',
    n_corp_val: '',
    n_caumo: '',
    s_nombre: '',
    n_broker: '',
    n_tipcam: ''    
  }
  

  export interface DataPost {
    [key: string]: any;
  }
   
  export const checks_list = ["n_guber","n_corpo","n_inter","n_acci","n_calif","n_tipcam",
                              "n_panam","n_catal","n_usr","n_cau","n_catpan","n_catcau",
                              "n_caumo","n_cr","n_catcal","n_corptrc","n_envios","n_hist_calif",
                              "n_hist_vec","n_deriv","n_bench","n_reginv","n_vmatriz","n_broker",
                              "n_notasdeuda","n_crv_der","n_corp_val","n_betas","n_pro_dev","n_users_web",
                              "n_der_valrisk","n_reuters","n_notas_est","n_mensajeria","n_ctlr_int",]