<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- 根元素  -->


    <xsl:template match="/">
        <xsl:apply-templates select="EntryDailyListReport"/>
    </xsl:template>

    <!--主模板//-->
    <xsl:template match="EntryDailyListReport">
        <xsl:processing-instruction name="cocoon-format">type="text/xslfo"</xsl:processing-instruction>
        <!--在此可以定义一些全局的风格信息，如字体等-->
        <fo:root font-family="KaiTi_GB2312" xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <!--版面定义//-->
            <fo:layout-master-set>
                <fo:simple-page-master
                    page-master-name="main"
                    margin-top="2cm"
                    margin-bottom="2cm"
                    margin-left="2cm"
                    margin-right="1.5cm">
                    <!--页眉//-->
                    <fo:region-before extent="0cm"/>
                    <!--主体//-->
                    <fo:region-body margin-top="0cm" margin-bottom="1cm"/>
                    <!--页脚//-->
                    <fo:region-after extent="0.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:apply-templates select="PageList"/>
        </fo:root>
    </xsl:template>
    

    <xsl:template match="PageList">
        <fo:page-sequence>
            <!--定义页面样式引用，可以为首页、单数页、偶数页定义不同的页面风格-->
            <fo:sequence-specification>
                <fo:sequence-specifier-alternating />
            </fo:sequence-specification>
            <!--页眉显示内容-->
            <fo:static-content flow-name="xsl-region-before">
                <fo:block line-height="1pt" font-size="1pt" text-align="end"></fo:block>
            </fo:static-content>
            <!--页脚显示内容-->
            <fo:static-content flow-name="xsl-region-after">
            <fo:block line-height="10pt" font-size="10pt" text-align="center">第<fo:page-number/>页  共 <fo:page-number-citation ref-id="endofdoc"/> 页</fo:block>
            </fo:static-content>
            <!--页面主体内容-->
            <fo:flow flow-name="xsl-region-body">
                <!--报表头-->
                <xsl:apply-templates select="ReportHeader"/>
                <!--报表体(若有多个部分内容，参照下面一行重复)-->
                <xsl:apply-templates select="ReportBody"/>
                <!--报表尾-->
                <xsl:apply-templates select="ReportFooter"/>
            </fo:flow>
        </fo:page-sequence>
    </xsl:template>
    
    
          <!--报表主体（一般只有一个表格）//-->
     <xsl:template match="ReportBody">
        <!--空行,第一联和后两联不同,头联空1行,后两联空2行,有动态xml控制-->
        
        <xsl:apply-templates select="TableBegin"/>
        <xsl:apply-templates select="TableContract"/>
        
        <!--
        <xsl:apply-templates select="TableEnd"/>
       -->
      
        
      </xsl:template> 
      
      
      
      
     <xsl:template match="TableContract"> 
     
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资人：以平台记录的投资流水号对应的投资人为准：</fo:block>
      
       <fo:block align="center" space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="InvestorTable"/>
      </fo:block>
      
       <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资接受人: 以平台记录的投资接受流水号对应的投资接受人为准:</fo:block>
       
      <fo:block align="center" space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="financier"/>
      </fo:block>
      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt"> 小贷公司：以平台公示的相关《担保承诺函》对应的小贷公司为准:</fo:block>
      
      <fo:block  space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="guaranteeLetter"/>
      </fo:block>
      
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资人、投资接受人、小贷公司都是在<xsl:value-of select="platformName"/>网站（以下称“<xsl:value-of select="productName"/>金融服务平台”）上合法注册的主体。投资人同意向投资接受人提供约定期限的投资，投资接受人承诺到期无条件回购投资人的全部投资权益（包含本金和收益），同时小贷公司为投资接受人向投资人到期无条件回购投资人的投资权益（包含本金及收益）提供履约连带责任担保。</fo:block>     
     
      <fo:block align="center" space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="investTable"/>
      </fo:block>
      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt"> 各方就上述投资权益回购履约担保事宜订立如下合同：</fo:block>
      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第一条  定义</fo:block>      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">除非缔约方另有约定，本合同下列术语定义如下:</fo:block>    
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1. 投资人：是指在<xsl:value-of select="productName"/>金融服务平台注册并同意相关服务协议，有合法来源的闲余资金通过<xsl:value-of select="productName"/>金融服务平台进行投资形成投资权益，获得一定回报的自然人或法人。</fo:block>    
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.投资接受人：是指在<xsl:value-of select="productName"/>金融服务平台注册并同意相关服务协议，有资金需求及合法资金用途，承诺在到期时间无条件回购投资人的投资权益（包含本金和收益），并获得小贷公司履约连带责任担保的自然人。</fo:block>      
        <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">3.小贷公司：是指在<xsl:value-of select="productName"/>金融服务平台注册并同意相关服务协议，完成对投资接受人的尽职调查并为其提供履约连带责任担保的小贷公司。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">4.筹集期：是指投资接受人在<xsl:value-of select="productName"/>金融服务平台发布资金需求申请时所设定的筹资时间段。投资人只能在该期限内投资，投资人资金在筹集期不产生收益。投资一旦发出，除投资未成功外，投资人不得撤资，同时委托<xsl:value-of select="productName"/>金融将资金划至投资接受人在第三方支付机构的资金托管账户，具体划转时间以<xsl:value-of select="productName"/>金融服务平台发布的信息为准。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">5.回购到期日：是指投资接受人无条件回购投资人的投资权益（包含本金及收益）并支付相关投资人的全部金额的最后时间。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">6.易极付：是指<xsl:value-of select="productName"/>金融服务平台合作的获央行支付牌照的第三方支付机构，所有<xsl:value-of select="productName"/>金融服务平台的服务接受方资金均托管在接受方在易极付开设的托管账户内，并由易极付完成服务接受方的所有资金清结算。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">7.本合同订立与登记：本合同以各方通过其<xsl:value-of select="productName"/>金融服务平台及易极付绑定的网银账户和对应的密钥电子签名的形式订立与登记合同。其中，投资人进行投资时，应在投资接受人资金筹集期内登录<xsl:value-of select="productName"/>金融服务平台阅读并确认合同内容，然后通过其在<xsl:value-of select="productName"/>金融服务平台及易极付绑定的网银账户和对应的密钥进行电子签名订立合同并划转资金；投资接受人于筹集期满次日登录<xsl:value-of select="productName"/>金融服务平台阅读并确认合同内容，然后通过其在<xsl:value-of select="productName"/>金融服务平台及易极付绑定的网银账户和对应的密钥进行电子签名确认合同并划转资金。小贷公司以针对投资接受人与本合同对应唯一编号的担保函订立合同。已订立的合同由<xsl:value-of select="productName"/>金融服务平台保管。</fo:block>  
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">8.账户：本合同所指的投资人、投资接受人、小贷公司账户专指合同各方与<xsl:value-of select="productName"/>金融服务平台及易极付注册用户名绑定的银行网银账户，该银行账户的开户姓名、身份证件号码与其绑定的<xsl:value-of select="productName"/>金融服务平台及易极付账户注册时使用的姓名及身份证件号码完全一致。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">9.次日：本合同提及的次日特指下一个工作日的工作时间，暂定9:00至17:00，具体以网站公告为准。</fo:block>
		      
       <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第二条  投资基本信息</fo:block>
       <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资人在本合同项下的投资对象、小贷公司、投资金额、约定收益、收益起始日、回购到期日，以<xsl:value-of select="productName"/>金融服务平台记录的流水号对应的投资对象、小贷公司、投资金额、约定收益、投资成功日、回购到期日为准。</fo:block>
	   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt"><xsl:value-of select="productName"/>金融平台确认投资接受人正常回购投资人的投资权益（包含本金及收益）后，委托易极付正常划款日为回购到期日届满后两个法定工作日之内。由小贷公司代偿的，为回购到期日届满后三个法定工作日之内。</fo:block>
       
       <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第三条  各方权利义务</fo:block>
       <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">（一）投资人的权利义务</fo:block>
       <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.承诺提供的信息真实有效，投资资金来源合法，自愿向投资接受人进行投资形成投资权益（包含本金和收益）。</fo:block>
	    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.在筹集期中，自愿登录<xsl:value-of select="productName"/>金融服务平台，将投资资金从绑定银行卡划入或代扣进入其在<xsl:value-of select="productName"/>金融服务平台注册时关联的易极付账户，并完成投资网签手续。</fo:block>
	    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">3.同意按本合约规定将已划入其易极付账户的资金划至投资接受人账户，同意此资金未划入投资接受人账户且在收益起始日之前不计利息。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">4.保证其账户状态正常，确保资金划入、划出交易的完成。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">5.当投资接受人或小贷公司按照本合同约定履行了偿付义务，投资人同意并在此不可撤销地授权由<xsl:value-of select="platformName"/>代其向小贷公司出具《解除担保责任告知函》或《代偿债务确认书》。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">6.若小贷公司在保后调查中发现投资接受人发生影响其履约能力的任一情况，投资人在此同意并不可撤销地授权由小贷公司代其宣布提前收回投资收益或回购日提前到期。</fo:block>
	   <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">（二）投资接受人的权利义务</fo:block>      
	   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.承诺提供的信息真实有效，不得隐瞒任何影响或可能影响其资信能力的事项。</fo:block>      
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.承诺接受投资后用途合法合规，保证投资人的投资权益不受侵害，并在回购到期日一次性回购投资人的投资权益（包含本金和收益）。</fo:block>      
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">3.在筹集期满次日登录<xsl:value-of select="productName"/>金融服务平台确认本合同，并支付相关费用。具体金额计算方式以<xsl:value-of select="productName"/>金融服务平台公告的《<xsl:value-of select="platformName"/>网站费用及其他规则》为准。</fo:block>      
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">4.在到期时间之前登录<xsl:value-of select="productName"/>金融服务平台无条件回购投资人的投资权益（包含本金及收益），及时将足额资金划入其在<xsl:value-of select="productName"/>金融服务平台关联的易极付账户并委托<xsl:value-of select="productName"/>金融服务平台通过易极付划转至对应投资人账户偿付投资人权益。</fo:block>      
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">5.应保证其银行账户状态正常，确保资金划入、划出交易的完成。</fo:block>      
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">6、当投资接受人不能对投资人进行按时、足额回购投资权益（含投资人本金和收益）时，投资接受人同意并在此不可撤销地授权由<xsl:value-of select="platformName"/>代其向小贷公司出具《代偿通知书》。</fo:block>  
		
		<fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">（三）小贷公司的权利义务 </fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.承诺提供的信息真实有效，具有合法的担保资质。</fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.对投资接受人资信情况进行尽职调查。</fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">3.向投资接受人收取担保费，具体费用与投资接受人另行约定。</fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">4.督促投资接受人按时、足额、无条件回购投资人的投资权益（包含本金及收益）。</fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">5.在投资接受人未按照本合同第三条（二）第4条的规定履行回购投资人权益（包含本金和收益）的义务时，按照其对投资接受人的履约担保条款，于到期时间后的三个工作日内向投资者足额代偿相关款项，具体的代偿金额计算方式以<xsl:value-of select="productName"/>金融服务平台公告的《<xsl:value-of select="platformName"/>网站费用及其他规则》为准。</fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">6.保证期间为本合同项下回购到期届满之日（即回购到期日）起两年。</fo:block> 
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">7. 小贷公司保证的范围包括《投资权益回购履约担保合同》项下的本金、收益、违约金。</fo:block> 
		
		
		<fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第四条  提前履约及违约责任</fo:block>
        <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.投资接受人可在正常投资期间任一工作日提前履约回购投资权益和保证投资人收回全部本金及收益。投资接受人提前履约的，应一次性按合同订立时约定的收益率及投资期限进行履约，具体金额计算方式以<xsl:value-of select="productName"/>金融服务平台公告的《<xsl:value-of select="platformName"/>网站费用及其他规则》为准。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.投资接受人未能于回购到期日履约的，小贷公司需按照其对投资接受人的履约担保条款向投资者代偿相关款项。小贷公司代偿后按照其与投资接受人事先约定的条款向投资接受人追偿该笔代偿金及违约金。具体计算方式以小贷公司与投资接受人事先约定的违约金标准为准。<xsl:value-of select="productName"/>金融服务平台有权将投资接受人的违约行为在网站上公布并降低投资接受人的信用级别。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">3.投资接受人未能于回购到期日履行回购义务,导致小贷公司承担履约担保责任代为回购投资权益的，投资接受人需承担小贷公司因追偿而产生的所有费用（包括并不限于诉讼费用、律师费用、鉴定费、差旅费、保全费等）。</fo:block>
		        
		<fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第五条  各方约定的其他事项</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.各方授予<xsl:value-of select="productName"/>金融服务平台向合作银行及其他机构查询并获取其真实身份及合同项下交易信息（不含密码）的权利。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.投资接受人及小贷公司对<xsl:value-of select="productName"/>金融服务平台记录的本合同投资总额项下的所有内容均予以承认并履行。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">3.投资接受人及小贷公司须确保一次性足额回购本合同投资总额项下所有投资人的投资权益，保证所有投资人收回全部本金及收益，否则视为违约，由此引起的法律后果及违约责任由投资接受人及小贷公司连带承担。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">4.投资接受人及小贷公司对于含本合同在内的投资总额项下本金及收益的回购兑付操作（包括但不限于正常回购兑付、代偿兑付等）须登录<xsl:value-of select="productName"/>金融服务平台，通过各自在<xsl:value-of select="productName"/>金融服务平台关联的易极付账户绑定的银行账户向其易极付账户划转款项来完成，否则视为违约，由此引起的法律后果及违约责任由投资接受人及担保方连带承担。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">5.本合同项下的收款、兑付等数据信息均以<xsl:value-of select="productName"/>金融服务平台生成并公布的内容为准。投资人、投资接受人、小贷公司可以随时登录<xsl:value-of select="productName"/>金融服务平台查阅投资、担保、收\付款信息。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">6.各方一致同意合同项下投资本金及收益通过以下路径兑付时约定如下：如兑付资金为小贷公司划至其易极付账户用于代偿兑付投资人本金及收益的，该小贷公司即享有向投资接受人追偿全部兑付资金的权利，投资接受人对此予以认可，且不存在任何异议，并承诺自小贷公司兑付投资人投资权益（含投资本金及收益）之后，自愿以小贷公司兑付的全部款项为基数，自兑付之日起以同期银行贷款利率的四倍为标准计算逾期利息一并支付给小贷公司。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">7.本合同项下的投资收益权不得私下转让，转让须经过小贷公司书面同意并在<xsl:value-of select="productName"/>金融网站平台登记方为有效。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">8.合同各方应对其他方提供的信息及本合同内容保密，未经其他方同意，任何一方不得向合同主体之外的任何人披露，但法律、行政法规另有强制性规定，或监管、审计等有权机关另有强制性要求的除外。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">9.合同各方知悉并遵守《<xsl:value-of select="productName"/>金融服务平台服务协议》、《“投融保”业务合作协议》、《<xsl:value-of select="productName"/>金融服务有限公司网站费用及其他规则》、的规定，前述文本的修改必须经过小贷公司书面同意，否则该等文本的修改对小贷公司不产生任何效力。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">10.本合同采用电子文本形式制成，并在<xsl:value-of select="productName"/>金融服务平台上保留存档。合同各方根据本合同定义的步骤在网站上完成合同订立后，即具有与手写签名同等的法律效力。各方同意：因履行本合同有争议的，以<xsl:value-of select="productName"/>金融服务平台所保留的合同等文件版本及网站解释为准。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">11.投资人、投资接受人及小贷公司如因其银行账户发生销户、换卡、挂失和司法冻结等情况时，为保证业务正常开展，应重新绑定其它正常银行账户。如未及时绑定银行账户造成资金无法出入账的，须及时通知并申请<xsl:value-of select="productName"/>金融服务平台协助完成后续处理，并承担由此产生的不利后果及相应责任。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">12.<xsl:value-of select="productName"/>金融服务平台及易极付只提供投资人、投资接受人、小贷公司及其绑定银行账户间的资金清结算服务，对业务中涉及的投资风险不承担任何担保、代偿的义务。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">13.<xsl:value-of select="productName"/>金融服务平台为投资人、投资接受人及小贷公司提供信息发布、合同订立与登记服务；并根据合同约定提供投资人、投资接受人及小贷公司在各自的<xsl:value-of select="productName"/>金融服务平台关联的易极付账户与各方绑定银行账户间的交易资金划转服务。投资人、投资接受人及小贷公司绑定账户的安全性须自行管理及承担责任。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">14.投资人、投资接受人及小贷公司应确保在<xsl:value-of select="productName"/>金融服务平台登记邮件、手机联系方式，并确保正确与通畅。<xsl:value-of select="productName"/>金融服务平台向各方登记联系方式发送的通知及信息，视为通知到达。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">15.合同各方应按照法律规定自行处理税收问题。</fo:block>
		
		<fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第六条  法律的适用和争议的解决</fo:block>		
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.《<xsl:value-of select="productName"/>金融服务平台服务协议》、《“投融保”业务合作协议》、《<xsl:value-of select="productName"/>金融服务有限公司网站费用及其他规则》为本合同附件，与本合同具同等法律效力。</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.本合同受中华人民共和国法律管辖并按中华人民共和国法律解释。合同履行中发生争议，可由各方协商解决，协商不成可向小贷公司所在地人民法院起诉。</fo:block>
		
		<fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">第七条  合同生效条款</fo:block>
		<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">1.本合同为附条件生效合同，生效条件为：本合同在筹集期内达到投资接受人本次投资最低筹集金额；小贷公司出具针对投资接受人及本合同涉及的投资人投资权益的担保函，并保存记录在<xsl:value-of select="productName"/>金融服务平台后，本合同生效。</fo:block>
<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">2.不符合前款条件的，视为投资人投资失败，本合同不生效，投资人的投资款项退回投资人在<xsl:value-of select="productName"/>金融服务平台关联的易极付账户。</fo:block>
<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资人签订日：以投资划账记录的时间为准</fo:block>
<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资接受人签订日：以投资接受划账记录的时间为准</fo:block>
<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">担保方签订日：以对本合同进行审核记录的时间为准</fo:block>

			
		
				
				
		   
			   	     
		     
    </xsl:template>   
      

    
    
    <xsl:template match="TableBegin">
    	<fo:table space-before.optimum="10pt" border-collapse="separate" font-size="10pt"  text-align="right"  border-width="0pt" border-style="solid" border-color="yellow">
	   <fo:table-column column-width="2cm"/>
	   <fo:table-column column-width="8cm"/>
	   <fo:table-column column-width="4cm"/>
	   <fo:table-column column-width="3cm"/>
       <!-- 定义表头 //-->
         <fo:table-header>
	     <fo:table-row height="10pt">
	         <fo:table-cell number-columns-spanned="2"  height="10pt">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	          <fo:table-cell >
	             <fo:block wrap-option="wrap">合同编号：</fo:block>
	         </fo:table-cell>
	          <fo:table-cell >
	             <fo:block text-align="center"  wrap-option="wrap"><xsl:value-of select="LETTERNO"/></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     </fo:table-header>
	     
	        <fo:table-body  text-align="left"  >
	     
	         <fo:table-row height="15pt">
		          <fo:table-cell  number-columns-spanned="4" >
		             <fo:block wrap-option="wrap" ></fo:block>
		          </fo:table-cell>
	          </fo:table-row>
	     
	     
	          <fo:table-row height="30pt">
		          <fo:table-cell number-columns-spanned="4"  height="20pt" >
		             <fo:block text-align="center"  wrap-option="wrap"  font-weight="bold" font-size="20pt">投资权益回购履约担保合同</fo:block>
		          </fo:table-cell>
	          </fo:table-row>
	          
         </fo:table-body>
      </fo:table>
   </xsl:template>
   
   
   
   
     <xsl:template match="InvestorTable">
        
        <fo:table space-before.optimum="2pt" border-collapse="separate" font-size="10pt" border-width="0.5pt" border-style="normal">
            <!-- 定义列（与实际列数严格一致） //-->
            <fo:table-column column-width="3.0cm"/>
            <fo:table-column column-width="3.0cm"/>
            <fo:table-column column-width="3.0cm"/>
            <!-- 定义表头 //-->
            <fo:table-header>
                <fo:table-row font-weight="bold" font-size="10pt" border-width="0.5pt" border-style="solid">
                
                    <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
                        <fo:block text-align="center"></fo:block>
                    </fo:table-cell>
                       
                    <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0.0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                        <fo:block text-align="center">投资人流水号</fo:block>
                    </fo:table-cell>
                   
                    <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                        <fo:block text-align="center">金额（元）</fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-header>
            <fo:table-body>
                <xsl:apply-templates select="InvestorRow"/>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    
    
      <!--显示表格每一行的模板//-->
    <xsl:template match="InvestorRow">
        <fo:table-row space-before.optimum="3pt" font-size="10pt">
        
           <fo:table-cell text-align="start" border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
                <fo:block text-align="center"   wrap-option="wrap" language="zh" >
                     
                </fo:block>
            </fo:table-cell>
         
            <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                <fo:block text-align="center"   wrap-option="wrap" language="zh" >
                    <xsl:value-of select="serialNO"/>
                </fo:block>
            </fo:table-cell>
        
            <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                <fo:block text-align="center">
                    <xsl:value-of select="amout"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
    
    <xsl:template match="financier">
        
        <fo:table space-before.optimum="2pt" border-collapse="separate" font-size="10pt" border-width="0.5pt" border-style="normal">
            <!-- 定义列（与实际列数严格一致） //-->
            <fo:table-column column-width="3.0cm"/>
            <fo:table-column column-width="6.0cm"/>
            <!-- 定义表头 //-->
            <fo:table-header>
                <fo:table-row font-weight="bold" font-size="10pt" border-width="0.5pt" border-style="solid">
                
                    <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
                        <fo:block text-align="center"></fo:block>
                    </fo:table-cell>
                 
                    <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                        <fo:block text-align="center">投资接受人流水编号</fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-header>
            
            <fo:table-body>
	             <fo:table-row space-before.optimum="3pt" font-size="10pt">
	                <fo:table-cell text-align="start" border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
	                <fo:block text-align="center"   wrap-option="wrap" language="zh" >
	                     
	                </fo:block>
		            </fo:table-cell>
		         
		            <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
		                <fo:block text-align="center"   wrap-option="wrap" language="zh" >
		                    <xsl:value-of select="serialNO"/>
		                </fo:block>
		            </fo:table-cell>
		            
		          </fo:table-row>   
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    
    
    <xsl:template match="guaranteeLetter">
        
        <fo:table space-before.optimum="2pt" border-collapse="separate" font-size="10pt" border-width="0.5pt" border-style="normal">
            <!-- 定义列（与实际列数严格一致） //-->
            <fo:table-column column-width="3.0cm"/>
            <fo:table-column column-width="6.0cm"/>
            <!-- 定义表头 //-->
            <fo:table-header>
                <fo:table-row font-weight="bold" font-size="10pt" border-width="0.5pt" border-style="solid">
                
                    <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
                        <fo:block text-align="center"></fo:block>
                    </fo:table-cell>
                 
                    <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                        <fo:block text-align="center">担保承诺函编号</fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-header>
            
            <fo:table-body>
	            <fo:table-row space-before.optimum="3pt" font-size="10pt">
	                <fo:table-cell text-align="start" border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
	                <fo:block text-align="center"   wrap-option="wrap" language="zh" >
	                     
	                </fo:block>
		            </fo:table-cell>
		         
		            <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
		                <fo:block text-align="center"   wrap-option="wrap" language="zh" >
		                    <xsl:value-of select="serialNO"/>
		                </fo:block>
		            </fo:table-cell>
	             </fo:table-row>   
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    
    
     <xsl:template match="investTable">
        
        <fo:table space-before.optimum="2pt" border-collapse="separate" font-size="10pt" border-width="0.5pt" border-style="normal">
            <!-- 定义列（与实际列数严格一致） //-->
            <fo:table-column column-width="3.0cm"/>
            <fo:table-column column-width="4.0cm"/>
            <fo:table-column column-width="8.0cm"/>
            <!-- 定义表头 //-->
            <fo:table-header>
            </fo:table-header>
            
            <fo:table-body>
	            <fo:table-row space-before.optimum="3pt" font-size="10pt">
	            
			                <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
		                        <fo:block text-align="center"></fo:block>
		                    </fo:table-cell>
		                    
	                         <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block text-align="left" margin-left="5px"  wrap-option="wrap" language="zh" >
				                     投资权益本金合计
				                </fo:block>
				            </fo:table-cell>
				        
				            <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block text-align="left" margin-left="5px" >
				                    <xsl:value-of select="DMONEY"/> 万元人民币
				                </fo:block> 
				            </fo:table-cell>
	             </fo:table-row>  
	             
	             
	              <fo:table-row space-before.optimum="3pt" font-size="10pt">
	              
			                <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
		                        <fo:block text-align="center" ></fo:block>
		                    </fo:table-cell>
		                    	              
	                         <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block text-align="left" margin-left="5px"  wrap-option="wrap" language="zh" >
				                     投资权益年化收益率
				                </fo:block>
				            </fo:table-cell>
				        
				            <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block text-align="left" margin-left="5px" >
				                    <xsl:value-of select="PER"/> %
				                </fo:block>
				            </fo:table-cell>
	             </fo:table-row>  
	             
	              <fo:table-row space-before.optimum="3pt" font-size="10pt">
	              
			                <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
		                        <fo:block text-align="center"></fo:block>
		                    </fo:table-cell>
		                    	              
	                         <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block  text-align="left" margin-left="5px"  wrap-option="wrap" language="zh" >
				                     投资期间
				                </fo:block>
				            </fo:table-cell>
				        
				            <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block text-align="left" margin-left="5px" >
				                    <xsl:value-of select="SYYYY"/> 年 <xsl:value-of select="SMM"/> 月 <xsl:value-of select="SDD"/>日—  <xsl:value-of select="EYYYY"/> 年 <xsl:value-of select="EMM"/> 月 <xsl:value-of select="EDD"/>日
				                </fo:block>
				            </fo:table-cell>
	             </fo:table-row>  
	             
	             <fo:table-row space-before.optimum="3pt" font-size="10pt">

			                <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
		                        <fo:block text-align="center"></fo:block>
		                    </fo:table-cell>
		                    	             
	                         <fo:table-cell text-align="start" border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block  text-align="left" margin-left="5px"  wrap-option="wrap" language="zh" >
				                     回购到期日
				                </fo:block>
				            </fo:table-cell>
				        
				            <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0pt" border-right-width="0.5pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
				                <fo:block text-align="left" margin-left="5px" >
				                      <xsl:value-of select="EYYYY"/> 年 <xsl:value-of select="EMM"/> 月 <xsl:value-of select="EDD"/>日 的下一工作日
				                </fo:block>
				            </fo:table-cell>
	             </fo:table-row>  
	              
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    
    

    
      
      
    <xsl:template match="TableEnd">
    
     <fo:table space-before.optimum="10pt" border-collapse="separate" font-size="10pt"  text-align="center"  border-width="0pt" border-style="solid" border-color="green">
	   <fo:table-column column-width="5cm"/>
	   <fo:table-column column-width="7cm"/>
	   <fo:table-column column-width="5cm"/>
       <!-- 定义表头 //-->
         <fo:table-header>
	     <fo:table-row height="20pt">
	         <fo:table-cell   height="100pt"   number-columns-spanned="3">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	      <fo:table-row height="10pt">
	         <fo:table-cell    >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap">担保人：<xsl:value-of select="ENTERPRISE"/>（公章）</fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	       <fo:table-row height="20pt">
	         <fo:table-cell   height="30pt"   number-columns-spanned="3">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	      <fo:table-row >
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="right"  wrap-option="wrap" ><xsl:value-of select="PYYYY"/>年<xsl:value-of select="PMM"/> 月<xsl:value-of select="PDD"/> 日</fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	     </fo:table-header>
	     <fo:table-body>
         </fo:table-body>
      </fo:table>
    </xsl:template>  
       
       
      <xsl:template match="ReportFooter">
      <fo:block id="endofdoc"></fo:block>
      </xsl:template>   
  
  
  </xsl:stylesheet>  
    