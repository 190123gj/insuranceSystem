var _lp = {
	week:{
		'Sun':'Sunday', 'Mon':'Monday', 'Tue':'Tuesday', 'Wed':'Wednesday', 'Thr':'Thursday', 'Fri':'Friday', 'Sat':'Saturday'
	},
	des:{
		unit:'unit',
		length:'length',
		character:' characters',
		min:'min',
		max:'max',
		password:'show password',
		m_password:'modify password',
		none:'none',
		always:'always',
		everyday:'everyday',
		weekly:'weekly',
		start:'Starting time',
		end:'End time',
		week:'week',
		note1:'must be less',
		note2:'Start drawing',
		note3:'Stop drawing',
		note4:'Remove region',
		note5:'Clear region',
		note6:'Dowse Device Access Protocol',
		idle:'Free',		
		p_type:'Protocol Type',	
		address:'Address',		
		user:'User',
		password:'Password',
		mobilephone:'Mobilephone',
		add_m:'Add Mobilephone',
		modify_m:'Modify Mobilephone',							
		submit:'Submit',
		status:{
			success:'Access Success',
			free:'Free',
			registering:'Accessing',
			reporting:'Accessing:(Reporting)',
			dns_failed:'Accessing:(DNS Failed)',	
			proxy_failed:'Accessing:(Proxy Failed)',
			route_failed:'Accessing:(Route Failed)',
			unreachable:'Accessing:(Unreachable)',
			no_service:'Accessing:(No Service)',
			invalid_puid:'Accessing:(Invalid PUID)',
			invalid_password:'Accessing:(Invalid Password)',
			forbidden_puid:'Accessing:(Forbidden PUID)',  	
			registered_puid:'Accessing:(Registered PUID)',
			registered_decid:'Accessing:(Registered DevID)',		
			puis_full:'Accessing:(PUID Full)',
			unkown:'Accessing:(Unkown Errors)'	
		}								
									
	},			
	y:'Yes',
	n:'No',
	title:'Network video surveillance system',
	frame:{
		notes:{
			support_title:'Your browser version is too low to normally use some functions, recommend upgrading your browser to IE9 or above version or chrome',
			waiting_title1:'Tips',
			error_title2:'Error',
			reboot_waiting:'Device is rebooting, please wait......',
			reboot_success:'Device rebooting success！',	
			login_waiting:'logging in, please wait......',
			query_waiting:'Querying, please wait......',
			error_msg1:'Login failure, please download and install plug-in, login again after restart browser.',
			error_msg2:'Login failure',
			error_msg3:'Your plug-in version is too low, please re-download and install it,login again after restart browser.',
			pu_online:'Online',
			pu_offline:'Offline'
            	
		},
		pulist:{
			title:'Device list',
			gr_col:{
				name:'Device name'
			}
		},
		login:{
			title:'Login',
			btn_login:'Login',
			btn_exit:'Exit',
			btn_download:'Plug-in download',
			ip:{
				fieldLabel: 'Address',
				missingMessage: 'Please input the platform address, it cannot be null'
			},
			port:{
				fieldLabel: 'Port',
				missingMessage: 'Please input the platform port, it cannot be null'
			},
			uid:{
                fieldLabel: 'User name',
                missingMessage: 'Please input user name'
			},
			epId:{
                fieldLabel: 'Enterprise ID',
                missingMessage: 'Please input enterprise ID, it cannot be null'
			},
			password:{
                fieldLabel: 'Password',
                missingMessage: 'Please input login password'
			},
			remember:{
                boxLabel: 'Remember password'
			},
			bfix:{
				fieldLabel:'Through NetGap'
			}
		},
		navbar:{
			st:{
				title:'System'
			},
			iv:{
				title:'Video'
			},
			ia:{
				title:'Accompanying sound'
			},
			ov:{
				title:'Output video'
			},
			oa:{
				title:'Talkback'
			},
			ptz:{
				title:'PTZ'
			},
			sp:{
				title:'Serial port'
			},
			idl:{
				title:'Alarm input'
			},
			odl:{
				title:'Alarm output'
			},
			sg:{
				title:'Storage'
			},
			wifi:{
				title:'WiFi'
			},
			wm:{
				title:'4G/3G'
			},
			dp:{
				title:'Display device'
			},
			gps:{
				title:'GPS'
			}		        
		},
		player:{
			title:'Real-time surveillance',
			cameraTitle:'Camera',
			audioOutTitle:'Talkback control',
			alertInTitle:'Alarm input',
			alertOutTitle:'Alarm output',
			cameralist:{
				streamType:'Video stream',
				btns:{
					playall:'Play all',
					stopall:'Stop all',
					play:'Play',
					stop:'Stop'
				},
				col:{
					name:'Name',
					op:'Operation'
				}
			},
			audioout_list:{
				col:{
					name:'Name',
					status:'Status',
					op:'Operation'
				},
				btns:{
					call:'Call',
					talk:'Talkback',
					closecall:'Close call',
					closetalk:'Close talkback'
				},
				status_map:{
					"Free":"Free",
					"TeamTalking":"Cluster talking",
					"Talking":"Talking",
					"Calling":"Calling"
				}
			},
			alertin_list:{
				col:{
					name:'Name',
					status:'Status',
					op:'Operation'
				},
				status_map:{
					"alert":"Alarm",
					"noalert":"No alarm",
					"valid":"Valid",
					"invalid":"Invalid"
				}
			},
			alertout_list:{
				col:{
					name:'Name',
					connect:'Connected',
					"break":'Disconnected',
					pulse:'Twinkling',
					status:'Status'
				}
			},
			ptzTitle:'PTZ control',
			prepostion:{
				lbl:'Preset',
				go:'Go to',
				set:'Set',
				rename:'Naming',
				renameTitle:'Rename preset name',
				name:{
					lbl:'Name'
				}
			},
			adl:{
				renameTitle:'Rename auxiliary device',
				lbl:'Auxiliary device',
				open:'Open',
				close:'Close',
				rename:'Naming',
				name:{
					lbl:'Name'
				}
			},
			tour:{
				setTitle:'Edit cruising line',
				lbl:'Cruising line',
				start:'Start',
				stop:'Stop',
				set:'Edit',
				add:'Add preset',
				name:{
					lbl:'Name'
				},
				edit_list:{
					col:{
						id:'Number',
						name:'Preset',
						linger:'Stay time',
						op:'Operation'
					}
				}
			},
			autoscan:{
				lbl:'Auto scan',
				start:'Start',
				stop:'Stop',
				seta:'Set start point',
				setb:'Set end point'
			},
			
			notes:{
				noVideo:'No video',
				snapshot:'Local snapshot',
				record:'Local video recording',
				playaudio:'Play audio',
				playupaudio:'Start calling',
				playtalk:'Start talking',
				stopvideo:'Close video',
				fullscreen:'Fullscreen',
				connecting:'Requesting video',
				streambreak:'Stream has been disconnected',
				buffering:'Buffering',
				playing:'Playing',
				downing:'Downloading',
				recording:'Recording',
				audioing:'Audio playing',
				upaudioing:'Calling',
				talking:'Talking',
				frame:'Frame',
				bite:'Bit rate',
				audioing:'Audio playing',
				playfailed:'Failed to play',
				noteTitle1:'Tips',
				note1:'Setting success.',
				noteError1:'Setting failure',
				noteError2:'Video playing window was not selected',
				noteError3:"Video is playing, please don't repeatedly play"
			},
			btns:{
				save:'Save',
				cancel:'Cancel',
				del:'Delete',
				ok:'Ok'
			}
		},
		vod:{
			title:'Query download',
			file_list:{
				col:{
    		        beginTime:'Start time',
    		        endTime:'End time',
    		        reason:'Reason',
					path:'Path',
					name:'File name',
					size:'Size',
					savePath:'Save address',
					status:'Staus',
					downloadLength:'Downloaded size',
					process:'Progress',
					op:'Operation'
				}
			},
			record:{
				filelist_title:'Video file',
				camera_title:'Resource list',
				title:'Video',
				fast_index:'Fast indexing',
				adv_query:'Advanced query'
			},
			snapshot:{
				filelist_title:'Picture file',
				title:'Picture',
				fast_index:'Fast indexing',
				adv_query:'Advanced query'
			},
			event:{
				title:'Event'
			},
			log:{
				title:'Logs'
			}
		},
		configsets:{
			title:'Device configuration',
			renameTitle:'Change name',
			setDescTitle:'Set description',
			setSchedule:'Set time',
			notes:{
				noteTitle:'Tips',	
				note:'Setting success',
				noteError:'Setting failure',
				note1:'Successfully set resource name',
				noteError1:'Failed to set resource name',
				note2:'Successfully set resource description',
				noteError2:'Failed to set resource description',
				note3:'Successfully enable resource',
				noteError3:'Failed to enable resource',
				noteTitle1:'Ok',
				note4:'Sure to delete the platform address information?',
				note5:'Successfully delete the platform address',
				note6:'Failed to send control command (Incorrect command)',
				note7:'Successfully send control command',
				note8:'Failed to send control command',
				note9:'Sure to reboot device?',
				note10:'Sure to restore factory settings?',
				note11:'Successfully add IP channel',
				note12:'Successfully modify IP channel',
				note13:'Successfully delete IP channel',
				note14:'Successfully add mobilephone number',
				note15:'Successfully modify mobilephone number',
				note16:'Successfully set backup disk',
				note17:'Sure to format disk?',
				note18:'Successfully format disk'
			},			
			btns:{
				rename:'Rename',
				disabled:'Disable',
				enable:'Enable',
				set:'Setting',
				refresh:'Refresh',
				add:'Add',
				modify:'Modify',
				clean:'Clear',
				del:'Delete',
				ok:'Submit',
				okall:'Submit to all channels',
				close:'Close',
				restore:'Restore factory settings',
				reboot:'Reboot',
				online:'Online',
				offline:'Offline',
				scanAP:'Scan WiFi hotspot',
				selectedAP:'Selection',
				aperture:'Automatic aperture correction',
				forceDay:'Compulsory day parameter',
				forceNight:'Compulsory night parameter',
				autoDayNight:'Auto day-night parameter',
				sendSPData:'Send serial port data',
				inData:'Input data (hexadecimal）',
				send:'Send',
				setBackup:'Set as backup disk',
				cancelBackup:'Cancel backup disk',
				formatSD:'Formatting',
				resetCounter:'Empty count value'
			},
			st:{
				name:{
					lbl:'Name'
				},
				idx:{
					lbl:'Index'
				},
				bEnable:{
					lbl:'Available or not'
				},
				desc:{
					lbl:'Description'
				},
				dt:{
					lbl:'Current time'
				},
				control:{
					lbl:'Control'
				}
			},
			sg:{
				dk_gr_title:'Disk list',
				dk_gr_col:{
					volumeLabel:'Volume label',
					letter:'Drive letter',
					backup:'Backup',
					type:'Type',
					fileSystem:'File system',
					usableSpace:'Free space',
					usedSpace:'Used space',
					capacity:'Capacity',
					status:'Status'
				}
			},
			ap:{
				gr_col:{
					ssid:'SSID',
					auth:'Authentication method',
					signal:'Signal strength',
					isuse:'In use or not',
					y:'Yes',
					n:'No'
				}
			},
			address_list:{
				col:{
					address:'Platform address',
					status:'Access status',
					op:'Operation'
				}
			},
			session_list:{
				col:{
					userId:'User name',
					clientType:'Client type',
					loginTime:'Login time',
					sourceAddr:'Source address'
				}
			},
			stream_list:{
				col:{
					resType:'Resource type',
					stream:'Resource index',
					resIdx:'Stream ID',
					createTime:'Create time',
					peerAddr:'Source address',
					totalBytes:'Number of bytes'
				}
			},
			address:{
				lbl:'Platform address',
				addTitle:'Add platform address',
				modifyTitle:'Modify platform address'			
			
			},
			ipchannelsets_list:{
				addTitle:'Add channel',
				modifyTitle:'Modify channel',
				col:{
					producerId:'Protocol type',
					addr:'Address',
					userId:'User',
					password:'Pasword',
					resIdx:'Resource number',
					connectStatus:'Connection status',
					usedCodeCapability:'Used code',
					usedBandwidth:'Used bandwidth',
					op:'Operation'
				},
				producers:['iCAP','ONVIF','Free']
			},
			devicelinkactions_list:{
				addTitle:'Add Linkaction',
					modifyTitle:'Midify Linkaction',
					col:{
						eventType:'Event Type',
						src:'Event Source',
						model:'Arming Mode',
						models:'Mode',	
						modelTime:'Arming Time',	
						action:'Linkage Action',
						actionParams:'Action parameters',
						alertOutStream:'Channel alarm output',
						alertOutAction:'Alarm output Action',
						inputVideoNo:'Video channel',
						recordTime:'Recording Duration',
						snapShotMumber:'SnapShot mumber',
						snapShotInterval:'SnapShot interval',
						presetPositionNo:'Preset number',
						recordUnit:'（M）',
						snapShotUnit:'（Sheet）',
						snapShotTime:'（MS）',											
						op:'operating'
					},
				alertOutStatus:{
					connect:'Connected',
					breaks:'Disconnected',
					pulse:'Twinkle'
				},		
				eventType:{
					E_ST_EmergentAlert:'Emergency alarm',
					E_ST_UnusableTimeAlert:'Unavailable time alarm',	
					E_ST_OverSpeed:'Overspeed alarm',	
					E_ST_FatigueDriving:'Fatigue driving alarm',
					E_ST_StationLinger:'Bus linger at station',
					E_ST_RTCFault:'Time error',	
					E_ST_BatteryOver:'Alarm for running out of battery charge',	
					E_ST_WirelessAlarmIn:'Wireless alarm alerts',	
					E_ST_InfraredBodyAlarm:'Built-in infrared body induction alarm',
					E_ST_VehicleBrake:'Vehicle emergency brake alarm',	
					E_ST_VehicleCollision:'Vehicle collision alarm',	
					E_ST_VehicleRollover:'Vehicle rollover alarm',	
					E_WM_BytesAlarm:'Early warning of traffic volume',
					E_IV_SignalLost:'Signal loss',	
					E_IV_MotionDetected:'Motion detected',	
					E_IV_CoverDetected:'Shield detected',
					E_IDL_AlertIn:'Alarm occured',
					E_IDL_HostAlarm:'Host alarm',	
					E_SG_DiskSpaceFull:'Disk space is full',	
					E_SG_StartFileSystemFailed:'Failed to load file system',	
					E_SG_DiskError:'Disk error',
					E_GPS_LowSpeed:'Idle alarm',	
					E_GPS_InERailAlarm:'Alarm to enter the electronic rail',	
					E_GPS_OutERailAlarm:'Alarm to leave the electronic rail'								
				},
				action:{
					alertOut:'Alarm output',
					sendEmail:'Send email and snapshot',	
					ftpUpload:'Upload the snapshot to FTP server',	
					record:'Start recording',
					snapShot:'Start snapshot',
					moveToPresetPosition:'Move to preset',	
					online:'Online',	
					sendSMS:'Send alarm message',	
					preTransmitVideo:'Pre-transmit video',
					preSnapshot:'Pre-snapshot',	
					preTransmitAudio:'Pre-transmit audio',	
					preTransmitGPS:'Pre-transmit location information',	
					uploadAlert:'Upload alarm information',
					uploadRecord:'Upload video and audio in real time',	
					playTipVoice:'Play voice prompt'							
				},
				model:{
					None:'Null',
					Always:'All',
					Everyday:'Daily',
					Weekly:'Weekly'
				},
				time:{
					time1:'Period 1',
					time2:'Period 2',
					time3:'Period 3'
				},
				week:{
					Sun:'Sunday',
					Mon:'Monday',
					Tues:'Tuesday',	
					Wed:'Wednesday',
					Thur:'Thursday',
					Fri:'Friday',
					Sat:'Saturday'		
				}												
			},								
			recordstatus_list:{
				col:{
					idx:'Channel',
					status:'Recording status',
					reason:'Recording reason',
					fault:'Fault cause',
					op:'Operation'
				}
			},
			onlinesmsets_list:{
				col:{
					id:'Number',
					phone:'Phone number',
					op:'Operation'
				}
			}
		},
		map:{
			title:'Electronic map'
		},
		decode:{
			title:'Video decoding',
			ov_list:'Video source list:',	
			notes:{
				noteTitle:'Refresh video source list',	
				note:"The video source list has been changed, sure you don't submit the change? ",
				note1:'Enable auto switch',
				note2:'Add video source--login',
				note3:'Add video source--select video',
				note4:'Select video source',
				note5:'Please select video source',
				note6:'Modify video source information',
				note7:'Delete video source',
				note8:'Sure to delete the selected video source?',
				note9:'Refresh video source list',
				note10:"Video source list has been changed, sure you don't submit the change?",
				note11:'Successfully add IP channel',
				note12:'Successfully modify IP channel',
				note13:'Successfully delete IP channel',
				note14:'Successfully add mobilephone number',
				note15:'Successfully modify mobilephone number',
				note16:'Successfully delete mobilephone number',
				note17:'Sure to format disk?'
			},
		　　btns:{
			　　rename:'Rename',
			　　disabled:'Disable',
			　　enable:'Enable',
			　　set:'Setting',
			　　query:'Query',	
			　　refresh:'Refresh',
			　　add:'Add',
			　　modify:'Modify',
			　　clean:'Clear',
			　　del:'Delete',
			　　ok:'Submit',
			　　confirm:'Ok',
			　　canael:'Cancel',
			　　yes:'Yes',
　　			no:'No',
　　			setmodify:'Save modification',		
			　　okall:'Submit to all channels',
			　　msg:'Numbered starting from 1',
			　　setdesc:'Set description'		
			},
			sources_list:{
			　　name:'Video name',
			　　decname:'Device name',
			　　idx:'Channel',
			　　stream:'Video stream',
			　　stream1:'Stream',	
			　　linger:'Stay time(s)',
			　　linger1:'Stay time',	
			　　addr:'Addres',
			　　password:'Password',
			　　remumber:'Remember password',			
			　　userid:'User name',
			　　autoswitch:'Auto switch',
			　　status:'Status',
			　　opt:'Operation'
			}			
		}
	}
}