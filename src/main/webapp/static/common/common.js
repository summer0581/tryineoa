//textarea双击弹出快捷输入“同意/不同意”(请假原因、外出事由除外),#reason$("#outReason")
$('textarea:not(#reason,#outReason)').live('dblclick', function() {
	var $currentDom = $(this);
	if (!$currentDom.attr("readonly")) {
		var submit = function(v, h, f) {
			if (v == true) {
				jBox.tip("同意", 'info');
				$currentDom.val("同意");

			} else {
				jBox.tip("不同意", 'info');
				$currentDom.val("不同意");

			}
			return true;
		};
		// 自定义按钮
		$.jBox.confirm('是否同意？', "快捷输入", submit, {
			top : "40%",
			buttons : {
				'同意' : true,
				'不同意' : false
			}
		});
	}
});