package pers.zr.vlumino.chinesechess.ai.zobrist;

public final class ZobristCode {

    public static final int[][] zobristCode = {
            {8758129, 7249344, 756459, 1872744, 7008760, 3900476, 5040686, 4965429, 5584047, 4438219, 857295, 4500503, 8599139, 3218304, 9567379, 3360650, 4744753, 6362627, 5832515, 9014645, 9551566, 3228020, 3757589, 1043717, 5014229, 1285446, 1816757, 5685664, 1533814, 9614998, 8909014, 810113, 8866009, 2716233, 774648, 2638712, 7303389, 8140009, 701097, 3736919, 7673616, 8567113, 956514, 329460, 8901432, 1390644, 1711586, 5829400, 4860822, 6603229, 2344940, 6621174, 8034736, 6846804, 5974729, 5600821, 6103303, 1851812, 1818738, 8752033, 8997427, 3174357, 1557654, 124546, 8183247, 4133925, 5260874, 981777, 6983886, 4160996, 9631499, 9597498, 598481, 6382955, 6336946, 9206742, 7144475, 2208378, 1013854, 2954024, 6527675, 6913912, 4170735, 6332330, 2235606, 5794593, 3659085, 9569249, 2253183, 1307592},
            {4643434, 4691996, 9215756, 7821039, 7073090, 4590960, 6643102, 2073672, 2543079, 4301810, 7638942, 4257755, 2129061, 7939098, 4794079, 6756798, 4290774, 8239641, 5197268, 5001282, 7260803, 9511916, 2436463, 7649732, 3470257, 8938045, 8264064, 1359104, 4510188, 5637858, 203341, 8777613, 3887371, 1679913, 7789206, 7074749, 1405291, 4312236, 9365027, 8317399, 7386008, 4085937, 3398177, 2307590, 7145775, 9257754, 3130572, 7116555, 6147086, 2882959, 1046407, 2481430, 4072098, 5347951, 117853, 6475046, 1697113, 1742268, 2080497, 6415921, 7895089, 432615, 9093795, 854692, 5352920, 6476910, 4057848, 654875, 2662465, 1104247, 6586312, 3679965, 7329105, 6387057, 6176284, 9405555, 8938846, 2444620, 1469500, 6894270, 4234762, 3042525, 4665219, 2002967, 6175840, 4290502, 3123668, 7435782, 7186054, 291480},
            {5989939, 2313258, 2748747, 9057614, 2271759, 6296911, 5261195, 1784397, 8220551, 6990257, 9636455, 8661872, 6093724, 8331433, 1524400, 4802358, 3417169, 1999220, 6221086, 576983, 1720408, 3473376, 1345369, 1678713, 6382904, 9321866, 9183959, 5567456, 4708528, 2531704, 5240862, 2603204, 7636205, 8256734, 7974420, 2136091, 1598278, 3224876, 964936, 6097588, 954129, 8228947, 2828123, 5225008, 8155146, 4619332, 2074400, 5951832, 8338093, 2523439, 3811300, 9209281, 7397499, 635967, 2696047, 5909952, 7753677, 4855703, 1867600, 7722227, 4011491, 5788672, 722242, 5894530, 1496035, 3194127, 5344059, 5351438, 4254867, 8818985, 4161408, 6059009, 5532482, 7747980, 7972748, 8744592, 2951220, 2390923, 7869980, 7456793, 2673808, 2535110, 6784678, 7164117, 1676265, 9510062, 392404, 616246, 4925904, 2443413},
            {932520, 5029776, 4737692, 9830916, 3135715, 5810623, 2873232, 200148, 4898695, 1488225, 2050157, 1516692, 6605283, 1312019, 2445732, 163139, 2065410, 4548611, 506986, 6815467, 928276, 8418388, 1920705, 8347946, 7123190, 9823276, 7950134, 2016867, 391402, 4542689, 4511250, 605065, 8294472, 6412622, 6339986, 4953294, 4219501, 8153242, 4392805, 3361204, 2848830, 4973205, 3226533, 5709774, 8321538, 1854327, 6519368, 3838715, 1886385, 5508121, 3123523, 8706490, 5230571, 2396115, 3080947, 9167239, 2865970, 4918740, 2887108, 6818049, 1964254, 8902980, 1953813, 6301620, 854600, 6856861, 7700586, 693564, 8032419, 3109255, 4995109, 7160163, 1974828, 8916341, 6766774, 8941599, 8318340, 7891500, 6533385, 5897283, 5026532, 4703261, 309590, 7344045, 8060191, 2886340, 4906615, 7724625, 9759710, 823702},
            {1986403, 7762990, 9659306, 8482937, 2399269, 1194701, 2774829, 1328180, 3312708, 9904645, 3541773, 1979153, 1429986, 8361119, 4527308, 4651812, 9200693, 1604386, 7799941, 2190487, 7341423, 9729455, 3412688, 589269, 6213266, 5796984, 3840388, 1300812, 643846, 3412848, 5409052, 7343584, 7635091, 4836063, 7764935, 5244015, 4064010, 406357, 253253, 1187873, 1962205, 5326883, 9711241, 6405571, 2250902, 394160, 7627531, 3779037, 3911781, 9136476, 5879565, 4933130, 4489910, 5003658, 9333055, 4662500, 9553593, 8745216, 472240, 7445177, 3276974, 7920531, 5926975, 9007052, 9664740, 7603892, 7272438, 4426824, 4104284, 3778828, 8799530, 3108856, 7909214, 8660321, 3088770, 101796, 4309094, 240132, 1167794, 2703683, 2943845, 6719592, 7792302, 1069680, 1337203, 175889, 3962592, 1626437, 6052210, 2801981},
            {8172536, 2497494, 3272671, 3366936, 5085188, 857031, 8408055, 1162620, 5338919, 3154637, 6985340, 9634313, 3010269, 491684, 4569792, 2136960, 7894462, 3080288, 1747707, 7815406, 8211815, 9678686, 1307647, 6476112, 5607997, 172315, 8672725, 7499488, 8388405, 2057709, 3365004, 9336109, 811664, 4486752, 8824088, 2936732, 5964521, 6741721, 255518, 4585173, 7250185, 1536912, 5350563, 1034735, 8844421, 7380566, 697560, 2593026, 8608469, 8381194, 7495932, 7856640, 7895133, 4345264, 3787549, 7166327, 1444138, 8788653, 6836111, 534901, 7135140, 7572107, 8513740, 9794287, 9751511, 6271386, 8594996, 4014760, 5105315, 105613, 5712030, 1868526, 883632, 6272807, 3390620, 4541503, 8398672, 4879381, 9065342, 8041235, 5058881, 1300864, 7263153, 4305551, 825488, 9773934, 4248151, 6385714, 3178752, 5295267},
            {336342, 4159444, 4190369, 8490602, 7817710, 3523329, 183343, 6017789, 6963925, 3820800, 9157811, 7280503, 9953550, 5380970, 3671137, 6754692, 2712595, 1475283, 7182831, 67690, 7646806, 6743061, 9615, 1120366, 6033082, 7193673, 6983221, 7005526, 7440931, 5262195, 2663291, 7251206, 4970893, 7077073, 1529986, 4005354, 9325367, 8092434, 9965704, 178832, 1768304, 7544335, 4032543, 3276510, 5883736, 1809376, 2061951, 3671670, 709136, 1675760, 3895223, 7416758, 3298103, 8781000, 9248311, 5334001, 7057902, 2027129, 6121656, 2092202, 3310887, 5653405, 6435525, 1251504, 7444867, 6828144, 143594, 4845735, 7745972, 2115460, 962299, 8972377, 4548605, 8979318, 6550727, 73090, 9690586, 606461, 3485587, 113087, 1427589, 9761404, 2698076, 2705567, 7939511, 130834, 4174218, 6377822, 3352408, 1671393},
            {667500, 5638467, 3803178, 9843960, 8932434, 5280700, 7490142, 7299183, 3559896, 8444337, 1881229, 573182, 2879893, 4359794, 3292482, 3658937, 8594654, 7410053, 8903079, 4938056, 814711, 5172051, 8939145, 3889238, 7992917, 9436828, 7237071, 9233899, 6886085, 858145, 1688147, 736058, 1121223, 4842187, 2169764, 2742719, 8203093, 3566349, 2484503, 8732650, 3872087, 6602995, 4556930, 2635563, 9606196, 524476, 7678909, 4490380, 3758077, 6078576, 2947816, 5130366, 9328033, 48948, 3207974, 7014600, 1695956, 7753000, 1784234, 6069352, 7770055, 1280067, 9087558, 2459599, 3957460, 8035606, 4776044, 6580054, 4121569, 3932546, 4570793, 236705, 263245, 5658800, 5054590, 1984018, 8047753, 6850354, 6822326, 9889600, 6496603, 3712221, 6205656, 4617242, 6173198, 6654955, 8460372, 650913, 3013484, 5852308},
            {783494, 3726431, 5587551, 275983, 1818114, 8094140, 2025391, 1726358, 5407501, 8105110, 9099809, 7653431, 276045, 4794972, 31717, 9150112, 4043096, 9633677, 9467839, 3400414, 9516775, 4416085, 3778088, 3077950, 3568366, 8387443, 2329677, 8715559, 4053897, 1129447, 717258, 3904754, 8228037, 2476726, 63170, 7334098, 6886462, 8790220, 7449139, 138487, 2076342, 8283075, 1300528, 1806713, 2630629, 6776309, 2116517, 2982578, 1010340, 9255656, 3324346, 8895211, 6980041, 1907617, 9233020, 2867142, 8495251, 3931901, 7071655, 5374069, 7846512, 8359085, 7919892, 468134, 1583372, 6346444, 6972573, 8026947, 6032562, 9981560, 7028158, 7253460, 6328134, 4492443, 350105, 4127184, 5787385, 1944567, 3122712, 3095148, 4194862, 4999318, 3607675, 1191385, 8097796, 1115043, 9804914, 986606, 3569803, 4690989},
            {4930925, 7295135, 2065725, 7086090, 9350252, 1404055, 2968982, 2188671, 5730374, 5739906, 2223831, 8269823, 137623, 1351181, 5048452, 5222283, 5551471, 360228, 2140774, 9968362, 5846810, 5626090, 3017493, 1814540, 7678627, 260180, 7167735, 3837038, 4900703, 349597, 3556336, 6001787, 5159777, 5268260, 1677731, 653870, 1128841, 1293948, 2839955, 1255642, 9478032, 5899712, 8649280, 809386, 9128822, 2051011, 4769511, 7269501, 6596996, 4115348, 1946008, 9430837, 302159, 9579004, 3012993, 8273979, 7606293, 3665953, 4143723, 5554871, 8041129, 7166833, 956631, 3545344, 3693888, 1311320, 5256547, 7262745, 3043195, 7355090, 6168632, 3271002, 7671744, 2702974, 213658, 7192104, 2229547, 9852781, 8476815, 5436106, 6781386, 9219670, 5438199, 5339427, 7244329, 1988842, 3261692, 1895338, 4005944, 4544697},
            {7379927, 8567173, 1011129, 1737511, 4066573, 4293948, 7615214, 2274075, 8353163, 8729710, 3323269, 5856609, 1126244, 3002079, 1170328, 8344695, 7531591, 3832073, 8944479, 8177806, 4915792, 4726479, 8714571, 4571469, 7451494, 9135629, 4196508, 5587919, 6321131, 6963632, 2376922, 2082550, 8874966, 7624698, 510231, 1740322, 7532793, 9958387, 2114879, 5941688, 9364671, 1748202, 5916830, 1035511, 264552, 9332139, 9823123, 4746780, 3421721, 850398, 7955590, 7909904, 398438, 5044635, 2534593, 1493675, 4538220, 1204763, 3974890, 8971930, 657704, 4853450, 1940534, 4494316, 2016042, 8065092, 7302943, 8024463, 5409310, 2935218, 9956069, 9638242, 7903413, 1647866, 264848, 6512685, 4197491, 6832662, 2557873, 7786557, 1900850, 1459957, 7522522, 4436411, 3109953, 8391489, 6620501, 3587398, 4588019, 5885740},
            {5714068, 9265966, 5578294, 6851225, 6977936, 4501976, 1823089, 6447659, 7869141, 2900661, 6714171, 4754035, 3436918, 1330248, 5666121, 8654319, 3576884, 9208928, 7782806, 9835602, 9968622, 4025292, 9678077, 3850169, 8382985, 7189194, 9915389, 8562126, 805239, 9085436, 8946996, 8980826, 8392422, 7290532, 8661922, 9477499, 668029, 7313207, 7692911, 3688123, 8554465, 932180, 3486087, 7024725, 8853401, 3230169, 9803056, 6648920, 5606485, 5760956, 8803698, 9005652, 9403600, 9936703, 148943, 5571757, 9153142, 1685669, 5666283, 3343059, 2088537, 5705794, 7036853, 7765410, 6857509, 5227558, 5691377, 2309185, 1425347, 1099898, 8643318, 693398, 2220703, 2225840, 1341084, 4884747, 3462226, 7899934, 8787147, 4428528, 5926570, 1437417, 9150461, 412515, 2553118, 3635475, 9889567, 421517, 4228006, 4069816},
            {5007302, 9209502, 2849801, 8005199, 3474033, 3497767, 351460, 6786418, 3084866, 8301837, 1737949, 9976661, 1057273, 6496351, 7396187, 8223036, 2529130, 2946385, 4943722, 6854448, 783135, 9465276, 3704824, 1554914, 8502122, 7103162, 5537456, 9105779, 7650790, 7827080, 4181827, 8153196, 6682882, 8522630, 1561006, 7232617, 7688860, 6428826, 3536361, 9869943, 2827648, 6611915, 9505309, 9479353, 8927031, 8890569, 9926182, 9990748, 1490790, 3744022, 3364730, 1665114, 4516713, 4166331, 9979085, 5394013, 508084, 3174104, 1494754, 2929784, 5852314, 4731677, 8668481, 4955963, 343871, 9226849, 5999584, 3682768, 6044679, 3311935, 19652, 1740441, 9903681, 7919046, 1876208, 4543585, 6598276, 7565123, 7123275, 9012558, 2913855, 8681403, 5641497, 7563347, 2643665, 4633661, 2432742, 2615264, 1469765, 343656},
            {5668905, 3516176, 4576842, 9978912, 1821864, 4747883, 5854167, 7047058, 8961317, 1549638, 6456999, 7412623, 8379513, 6685706, 2227255, 2248297, 1586620, 9983245, 4014266, 9739334, 4417768, 479671, 2289987, 1478899, 2502561, 9544087, 7619006, 1923801, 2670115, 2175213, 1522242, 7024199, 2997441, 2828670, 8064222, 1197516, 156281, 1589350, 5097358, 185919, 6473224, 6280001, 5512630, 1018373, 690257, 6185042, 3074639, 9292433, 5797610, 3469006, 4459972, 8297541, 4508067, 9081921, 8971363, 4120776, 7951647, 969318, 66128, 5807619, 5991853, 3742422, 8315885, 9894451, 8199428, 614528, 3705583, 4186125, 2966837, 372397, 6283366, 6521116, 8506456, 4426930, 8088825, 448381, 3547717, 1575270, 6015718, 1234189, 7433218, 1006846, 4806218, 2423864, 9011073, 7298797, 1727310, 5302457, 1230554, 4671581}
    };

    public static int getZobristOfId(int id, int x, int y) {
        int a = id < 0 ? id + 7 : id + 6;
        return zobristCode[a][x * 9 + y];
    }
}
