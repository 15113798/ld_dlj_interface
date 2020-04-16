<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="行业名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="行业名称"></el-input>
    </el-form-item>
    <el-form-item label="Excel中的行业名称" prop="overName">
      <el-input v-model="dataForm.overName" placeholder="Excel中的行业名称"></el-input>
    </el-form-item>
    <el-form-item label="父级id" prop="pid">
      <el-input v-model="dataForm.pid" placeholder="父级id"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          overName: '',
          pid: ''
        },
        dataRule: {
          name: [
            { required: true, message: '行业名称不能为空', trigger: 'blur' }
          ],
          overName: [
            { required: true, message: 'Excel中的行业名称不能为空', trigger: 'blur' }
          ],
          pid: [
            { required: true, message: '父级id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/generator/dljindustry/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.dljIndustry.name
                this.dataForm.overName = data.dljIndustry.overName
                this.dataForm.pid = data.dljIndustry.pid
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/generator/dljindustry/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'overName': this.dataForm.overName,
                'pid': this.dataForm.pid
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
