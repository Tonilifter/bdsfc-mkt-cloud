package com.carrefour.bigdata.ssff.marketingcloud

object Queries {

  def getInfoContracts(): String = {

    s""" SELECT ido_contrato,substr(ido_contrato,1,12) as id_ssff,
               ido_baremo,
               imp_cma,num_tasa_mensual,teg,ido_seguro,
               imp_prima_mens_seg,ido_agencia,
               ido_financiacion,flg_gratuito,ido_producto,
               des_producto,ido_campana,imp_descubierto,imp_descubierto_origen,
               fch_entrada,fch_financiacion,imp_financiaciones,imp_mensualidad,
               ido_material,num_financiaciones,num_mensualidades,
               ido_vendedor,
               imp_mensualidades_tot,ido_origen,ido_tarjeta,ido_tarjeta_cot,
               flg_ipd,fch_ipd,fch_posicion_act,fch_ult_pago,
               imp_ult_pago,posicion_ant,posicion_act,imp_saldo_cliente,
               fch_bloqueo,fch_caducidad,imp_gast_retraso,flg_tj_anulada,flg_tj_anulada_cot,
               fch_ampliacion,seleccion,imp_limite,
               imp_disponible,fch_ult_compra,num_scli,imp_usos,
               num_usos,ido_cliente,ult12_estados,todu_con,
               num_tae,flg_correo_dev,ido_estados_24,
               ido_agente,ido_agente_electro,
               ido_fidelizacion,imp_deuda_contado,
               flg_seguro_tarjeta,firma_bascula,
               fch_firma_bascula,num_compras,imp_compras,num_dev_compras,imp_dev_compras,
               num_devos,imp_devos,num_pagos,imp_pagos,num_comi_devo,
               dispositivo,banco,year,month,day
        FROM ssff_user.contratos_actual
       """.stripMargin
  }

  val SB_SS_TABLES = Seq[String]("ssff_user.contratos_actual")

}