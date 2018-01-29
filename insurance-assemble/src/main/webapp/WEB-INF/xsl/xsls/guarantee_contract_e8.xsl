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
        
        <!-- 屏蔽结尾处  盖章、签名 
        <xsl:apply-templates select="TableEnd"/>
         -->
      
        
      </xsl:template> 
      
      
      
      
     <xsl:template match="TableContract"> 
     
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">甲方：向发起借款标的借款人提供资金的投资人</fo:block>
      
 
       <fo:block align="center" space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="InvestorTable"/>
      </fo:block>

      
       <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">乙方：发起借款标的借款人</fo:block>
     

      <fo:block align="center" space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="financier"/>
      </fo:block>

      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt"> 丙方：<xsl:value-of select="platformName"/></fo:block>
      
      <!--
      <fo:block  space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="guaranteeLetter"/>
      </fo:block>
      -->
       <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt"> 丁方：承诺为乙方提供后备贷款服务的小额贷款公司</fo:block>
  
      <fo:block align="center" space-before="8pt" space-after="2pt" >
       <xsl:apply-templates select="investTable"/>
      </fo:block>
      
      
      				<fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   鉴于：</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   1、甲方具有有合法来源并有权独立支配的闲置资金，自愿投资给乙方经营周转与消费使用；</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   2、乙方因合法的经营活动及消费需要筹借资金临时周转，自愿向甲方借款；</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  3、丙方基于甲方和乙方的委托，撮合甲乙双方依法建立借款法律关系，并为双方借款合同签订、借款交易完成、债权风险范防措施设定提供居间服务；</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   4、丁方是依据中华人民共和国法律及地方行政法规规章设立的从事小额贷款业务的企业法人，愿意为乙方提供融资咨询服务、后备贷款服务以保证乙方偿还对甲方债务所需资金来源，以确保甲方按时收回投资款本息。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   	四方为借款与服务事宜，经平等协商一致签订本合同，以确认各方权利与义务。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第一条	承诺与保证</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   1、甲方保证提供给乙方的资金系合法取得，并且对其具有独立支配权，履行本合同不损害任何他人利益。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  2、甲方承诺，对本合同约定投资可能产生的风险已有充分认识，并且确认在明知风险的情况下进行投资。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   3、乙方承诺向甲方借款的原因是合法的经营与消费活动，不将借款的全部或部分用于任何违法活动，并承诺严格遵守本合同约定按时还本付息。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   4、甲方和乙方均承诺，为履行本合同，自愿接受丙方指定的依法取得第三方支付牌照的重庆易极付科技有限公司（以下称“易极付”）提供的支付服务，并自愿遵守“易极付”的相关规则（尤其包括支付手续费的条件与标准），自行在“易极付”开立专用账户用于本合同履行过程中的资金划转。本合同所称在“易极付”开立的账户，均指各方在“易极付”开立的唯一有效账户。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  5、丙方向各方承诺，本合同约定“易极付”提供的服务均能够获得“易极付”的确认和执行。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  第二条	委托关系确认</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                    1、为投资事宜，甲方确认委托丙方联系借款人、确认与乙方建立借款合同关系是经丙方撮合而实现。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   2、为借款事宜，乙方确认委托丙方联系投资人、确认与甲方建立借款合同关系是经丙方撮合而实现。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第三条	借款金额、利率与期限</fo:block> 
                    
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   1、乙方向甲方借款的金额，为甲方在<xsl:value-of select="productName"/>网通过规定程序操作自行确认并实际转入乙方账户的金额。</fo:block>
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  2、乙方向甲方借款的利率和期限，按借款人在<xsl:value-of select="productName"/>网发布的“借款标”明示的利率与期限确定，借款期限自甲方资金从甲方在“易极付”的账户转出之日起算，并且该日期也是甲方利息收益的起算日。</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  3、乙方发布的“借款标”以“月”为期限计算单位，期限届满日为月数届满的那一月中与收到借款日对应的“日”，该月无此对应日的，为该月最后一日。</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                 第四条	甲方投资款支付</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                    1、基于乙方的保证和承诺，为帮助乙方解决经营活动或者消费需要而产生的临时资金困难，甲方自愿向乙方投资。为便于表述，本合同中“投资”的概念，均特指甲方向乙方提供资金供乙方经营活动或者消费周转使用，乙方按约定期限还本付息的民间借款行为。本合同所称“投资”，不包含针对乙方特定经营项目与用途投入资金并承担项目风险的含义。</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  2、甲方对乙方投资款，由甲方在“易极付”开立的账户转入乙方在“易极付”开立的账户。甲方投资款到达乙方在“易极付”开立的账户，则表明乙方已收到甲方的投资款。</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   3、甲方在<xsl:value-of select="productName"/>网确认投资后，“易极付”将对甲方账户等额资金予以冻结。如果甲方在“易极付”的账户中实有资金额低于甲方确认的投资额，则甲方投资指令不能得到系统确认。甲方资金冻结期间，无权获得利息或者其他收益。</fo:block>  
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   4、甲方确认投资且其在“易极付”账户的资金被“易极付”冻结后，但乙方“借款标”在2日内却没有筹满，则丙方将对乙方本次“借款标”予以撤标，丙方撤标的同时指令“易极付”解除对甲方账户资金的冻结。</fo:block>  
                   
    
                        
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  第五条	还本付息</fo:block> 
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  1、乙方支付甲方利息的方式，按<xsl:value-of select="productName"/>网公示的乙方“借款标”明示的方式执行。</fo:block> 
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  2、乙方向甲方支付利息以及归还甲方投资款，由乙方在“易极付”开立的账户转入甲方在“易极付”开立的账户。乙方还本付息的资金到达甲方在“易极付”开立的账户，则表明甲方已收到乙方还本付息款，乙方已履行还本付息的义务。</fo:block> 
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   3、乙方应在借款期限届满日12：00时前将还本付息的资金转入在“易极付”的账户，并自行发出指令将资金转入甲方在“易极付”的账户。</fo:block> 
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   4、乙方提前归还全部本金的，应按约定期间全额支付利息，并且利息与本金一并支付。如果乙方提前归还全部本金但未将约定期间的全部利息一并支付，“易极付”将拒绝受理。</fo:block> 
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   5、本合同一经成立，甲方和乙方均不接受对方收款账户变更。</fo:block> 
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第六条	逾期还本付息的违约责任</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                 	 乙方逾期偿还借款本息的，应自借款期限届满之次日起按逾期支付利息或者逾期归还本金额的每日万分之五向甲方支付逾期利息。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  第七条	避免逾期还本付息的保障措施</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                    1、如果约定的期限届满乙方不能足额偿还借款本息，则丁方应在还款期限届满日12：00时前向乙方发放不低于乙方应偿还债务本息金额的后备贷款，并且用于乙方偿还对甲方借款本息。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                    2、丁方与乙方确认，双方之间关于后备贷款发放的借款合同已签订并生效且担保措施已落实，只要发生乙方到期不能偿还借款本息的情况，丁方将立即向乙方发放后备贷款，并确保该贷款用于归还甲方借款本息，以保证甲方投资款本息按时收回。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   3、如果在本合同约定的还款期限届满日12：00时前乙方仍未足额偿还甲方借款本息，则丁方自动加入到甲方与乙方形成的债权债务关系中，与乙方构成共同债务人，与乙方共同对甲方承担偿还借款本息的责任，以及共同承担本合同约定的违约责任。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  4、如果发生乙方逾期偿还借款本息的情况，不仅丁方自动与乙方成为甲方的共同债务人，丙方有义务协调、督促以及采取其他有效措施督促丁方立即履行还款义务，以确保甲方收回投资本息不会发生过度迟延。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第八条	服务费与税金</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  1、就丙方提供的居间服务，甲乙双方均应向丙方支付服务费，但为简化操作，甲方应付服务费采用让渡利息的方式转移给乙方，由乙方向丙方支付应由双方支付的居间服务费，双方承担但由乙方向丙方支付的居间服务费的标准为借款额的3%。本合同第二条约定的利率，是甲方已经让渡利息以后的执行利率，因此，实际由乙方单方向丙方支付居间服务费对本合同第二条约定利率不产生任何影响。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   2、乙方应付甲方居间服务费，应在收到借款时支付，为此，乙方应授权丙方指令“易极付”在向乙方转付借款时支付给丙方。此项授权，乙方根据“易极付”的要求签订协议确认或者出具授权书进行授权。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   3、就丁方提供的咨询服务、后备贷款服务等服务，乙方应向丁方支付综合服务费，有关费率与支付办法，丁方与乙方另行约定。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   4、甲方因履行本合同而获得的收入，自行依法纳税。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第九条	争议解决</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  1、因履行本合同发生争议，各方应首先本着友好合作、避免与减少损失的原则协商处理。确实不能协商一致的，任何一方应通过向丙方所在地人民法院提起诉讼解决。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   2、任何一方提起诉讼的，败诉一方应承担胜诉一方因本案而产生的合理的律师服务费。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第十条	附件</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   	乙方在<xsl:value-of select="productName"/>网发布的借款标及相关附件均为本合同的附件，构成本合同的组成部分。</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   第十一条	合同生效</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   	本合同的签署方式为：</fo:block>  
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   	甲方和乙方，通过在“<xsl:value-of select="productName"/>网”按设定程序进入账号进行相关操作，确认承认本方为本合同的对应当事人，享受本合同约定的权利，承担本合同约定的义务。甲方和乙方自行妥善保管账号和密码，并承担相应责任。丙方通过甲方和乙方的账户及密码来识别甲方和乙方的指令，甲方和乙方确认，使用的账户和密码登陆后在<xsl:value-of select="productName"/>网的一切行为均代表甲方和乙方本人。</fo:block>  
                   
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   	丙方作为<xsl:value-of select="productName"/>网的建设与管理者，在投资无忧网系统中公示本合同，表明确认本合同对本方的约束力，享受本合约定的权利和履行本合同约定义务。</fo:block>  
                   
                   
                    <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   	丁方在<xsl:value-of select="productName"/>网上随借款标公示的《承诺书》，确认就该借款标承诺本合同对本方的约束力，按本合同丁方当事人享受权利和履行义务。</fo:block>   
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  	 乙方发出借款标、丙方公示本合同电子文本或者对书面文本盖章扫描后的扫描文本、丁方在《承诺书》中对本合同的确认均视为向甲方发出邀约，甲方在系统中确认向乙方投资表明对乙、丙、丁三方签订合同的要约的承诺，本合同自甲方确认完成时依法成立并立即生效。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                 	 各方签署程序完成时，为本合同成立并生效的时间。</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  甲方：向借款标明示的借款人提供资金的投资人</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                  乙方：通过<xsl:value-of select="productName"/>网发出借款标的借款人</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   丙方：<xsl:value-of select="platformName"/></fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
                   丁方：承诺为乙方提供后备贷款服务的小额贷款公司</fo:block> 
                   
                   <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="25px"  space-before="8pt" space-after="2pt"  line-height="17pt">
      </fo:block> 
                                                
     
     
      
    
			
		
				
				
		   
			   	     
		     
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
	         <fo:table-cell number-columns-spanned="4"  height="10pt">
	             <fo:block text-align="left"  wrap-option="wrap"> 编号： <xsl:value-of select="LETTERNO"/></fo:block>
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
		             <fo:block text-align="center"  wrap-option="wrap"  font-weight="bold" font-size="20pt">借款与服务合同</fo:block>
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
            <fo:table-column column-width="3.0cm"/>
            <!-- 定义表头 //-->
            <fo:table-header>
                <fo:table-row font-weight="bold" font-size="10pt" border-width="0.5pt" border-style="solid">
                
                    <fo:table-cell border-color="black" border-left-width="0pt" border-top-width="0pt" border-right-width="0.0pt" border-bottom-width="0pt" border-style="solid" height="10pt">
                        <fo:block text-align="center"></fo:block>
                    </fo:table-cell>


                    <fo:table-cell border-color="black" border-left-width="0.5pt" border-top-width="0.5pt" border-right-width="0.0pt" border-bottom-width="0.5pt" border-style="solid" height="10pt">
                        <fo:block text-align="center">投资人账户名</fo:block>
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
                    <xsl:value-of select="investorAccount"/>
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
                        <fo:block text-align="center">借款人账户编号</fo:block>
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
    