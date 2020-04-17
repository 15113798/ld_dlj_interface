<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="行业id" prop="industryName">
      <el-input v-model="dataForm.industryName" placeholder="行业id"></el-input>
    </el-form-item>
    <el-form-item label="用户数" prop="userNum">
      <el-input v-model="dataForm.userNum" placeholder="用户数"></el-input>
    </el-form-item>
    <el-form-item label="装机容量" prop="installedCapacity">
      <el-input v-model="dataForm.installedCapacity" placeholder="装机容量"></el-input>
    </el-form-item>
    <el-form-item label="用电量本月" prop="eleConMonth">
      <el-input v-model="dataForm.eleConMonth" placeholder="用电量本月"></el-input>
    </el-form-item>
    <el-form-item label="用电量上年同月" prop="eleConOldYearMonth">
      <el-input v-model="dataForm.eleConOldYearMonth" placeholder="用电量上年同月"></el-input>
    </el-form-item>
    <el-form-item label="本月同比" prop="compareMonth">
      <el-input v-model="dataForm.compareMonth" placeholder="本月同比"></el-input>
    </el-form-item>
    <el-form-item label="用电量本年" prop="eleConYear">
      <el-input v-model="dataForm.eleConYear" placeholder="用电量本年"></el-input>
    </el-form-item>
    <el-form-item label="用电量上年" prop="eleConOldYear">
      <el-input v-model="dataForm.eleConOldYear" placeholder="用电量上年"></el-input>
    </el-form-item>
    <el-form-item label="本年同比" prop="assYear">
      <el-input v-model="dataForm.assYear" placeholder="本年同比"></el-input>
    </el-form-item>
    <el-form-item label="行业产能利用率" prop="industryCapUtil">
      <el-input v-model="dataForm.industryCapUtil" placeholder="行业产能利用率"></el-input>
    </el-form-item>
    <el-form-item label="记录时间" prop="recordTime">
      <el-input v-model="dataForm.recordTime" placeholder="记录时间"></el-input>
    </el-form-item>
    <el-form-item label="环比" prop="chainRatio">
      <el-input v-model="dataForm.chainRatio" placeholder="环比"></el-input>
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
          industryName: '',
          userNum: '',
          installedCapacity: '',
          eleConMonth: '',
          eleConOldYearMonth: '',
          compareMonth: '',
          eleConYear: '',
          eleConOldYear: '',
          assYear: '',
          industryCapUtil: '',
          recordTime: '',
          chainRatio: ''
        },
        dataRule: {
          industryName: [
            { required: true, message: '行业id不能为空', trigger: 'blur' }
          ],
          userNum: [
            { required: true, message: '用户数不能为空', trigger: 'blur' }
          ],
          installedCapacity: [
            { required: true, message: '装机容量不能为空', trigger: 'blur' }
          ],
          eleConMonth: [
            { required: true, message: '用电量本月不能为空', trigger: 'blur' }
          ],
          eleConOldYearMonth: [
            { required: true, message: '用电量上年同月不能为空', trigger: 'blur' }
          ],
          compareMonth: [
            { required: true, message: '本月同比不能为空', trigger: 'blur' }
          ],
          eleConYear: [
            { required: true, message: '用电量本年不能为空', trigger: 'blur' }
          ],
          eleConOldYear: [
            { required: true, message: '用电量上年不能为空', trigger: 'blur' }
          ],
          assYear: [
            { required: true, message: '本年同比不能为空', trigger: 'blur' }
          ],
          industryCapUtil: [
            { required: true, message: '行业产能利用率不能为空', trigger: 'blur' }
          ],
          recordTime: [
            { required: true, message: '记录时间不能为空', trigger: 'blur' }
          ],
          chainRatio: [
            { required: true, message: '环比不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/generator/dljindustrydata/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.industryName = data.dljIndustryData.industryName
                this.dataForm.userNum = data.dljIndustryData.userNum
                this.dataForm.installedCapacity = data.dljIndustryData.installedCapacity
                this.dataForm.eleConMonth = data.dljIndustryData.eleConMonth
                this.dataForm.eleConOldYearMonth = data.dljIndustryData.eleConOldYearMonth
                this.dataForm.compareMonth = data.dljIndustryData.compareMonth
                this.dataForm.eleConYear = data.dljIndustryData.eleConYear
                this.dataForm.eleConOldYear = data.dljIndustryData.eleConOldYear
                this.dataForm.assYear = data.dljIndustryData.assYear
                this.dataForm.industryCapUtil = data.dljIndustryData.industryCapUtil
                this.dataForm.recordTime = data.dljIndustryData.recordTime
                this.dataForm.chainRatio = data.dljIndustryData.chainRatio
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
              url: this.$http.adornUrl(`/generator/dljindustrydata/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'industryName': this.dataForm.industryName,
                'userNum': this.dataForm.userNum,
                'installedCapacity': this.dataForm.installedCapacity,
                'eleConMonth': this.dataForm.eleConMonth,
                'eleConOldYearMonth': this.dataForm.eleConOldYearMonth,
                'compareMonth': this.dataForm.compareMonth,
                'eleConYear': this.dataForm.eleConYear,
                'eleConOldYear': this.dataForm.eleConOldYear,
                'assYear': this.dataForm.assYear,
                'industryCapUtil': this.dataForm.industryCapUtil,
                'recordTime': this.dataForm.recordTime,
                'chainRatio': this.dataForm.chainRatio
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
